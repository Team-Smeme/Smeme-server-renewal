package com.smeem.application.domain.diary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smeem.application.config.SmeemProperties;
import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.badge.BadgeType;
import com.smeem.application.domain.member.Member;
import com.smeem.application.port.input.DiaryUseCase;
import com.smeem.application.port.input.dto.request.diary.WriteDiaryRequest;
import com.smeem.application.port.input.dto.response.diary.CoachDiaryResponse;
import com.smeem.application.port.input.dto.response.diary.RetrieveDiariesResponse;
import com.smeem.application.port.input.dto.response.diary.RetrieveDiaryResponse;
import com.smeem.application.port.input.dto.response.diary.WriteDiaryResponse;
import com.smeem.application.port.output.persistence.*;
import com.smeem.application.port.output.web.openai.OpenAiPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService implements DiaryUseCase {
    private final DiaryPort diaryPort;
    private final MemberPort memberPort;
    private final BadgePort badgePort;
    private final MemberBadgePort memberBadgePort;
    private final TopicPort topicPort;
    private final SmeemProperties smeemProperties;
    private final OpenAiPort openAiPort;
    private final ObjectMapper objectMapper;

    @Transactional
    public WriteDiaryResponse writeDiary(long memberId, WriteDiaryRequest request) {
        val member = memberPort.findById(memberId);
        val topic = request.topicId() != null ? topicPort.findById(request.topicId()) : null;
        val savedDiary = diaryPort.save(request.toDomain(member, topic));

        val diaryWrittenYesterday = diaryPort.isExistByMemberAndYesterday(memberId);
        memberPort.update(member.updateDiaryComboCount(diaryWrittenYesterday));

        val requiredBadges = acquireBadgeOfWritingDiary(member);

        return WriteDiaryResponse.of(savedDiary, requiredBadges);
    }

    private List<Badge> acquireBadgeOfWritingDiary(Member member) {
        val acquiredBadges = new ArrayList<Badge>();
        badgePort.findByBadgeTypeAndStandard(BadgeType.COUNTING, diaryPort.countByMember(member.getId()))
                .ifPresent(acquiredBadges::add);
        badgePort.findByBadgeTypeAndStandard(BadgeType.COMBO, member.getDiaryComboCount())
                .ifPresent(acquiredBadges::add);

        val acquiredBadgeIds = memberBadgePort.findIdsByMember(member.getId());

        return acquiredBadges.stream()
                .filter(badge -> !acquiredBadgeIds.contains(badge.getId()))
                .peek(badge -> badgePort.saveAcquiredBadge(member.getId(), badge))
                .toList();
    }

    public RetrieveDiaryResponse retrieveDiary(long diaryId) {
        return RetrieveDiaryResponse.of(diaryPort.findByIdJoinMemberAndTopic(diaryId));
    }

    @Transactional
    public void modifyDiary(long diaryId, WriteDiaryRequest request) {
        val foundDiary = diaryPort.findById(diaryId);
        diaryPort.update(request.toDomain(foundDiary));
    }

    @Transactional
    public void deleteDiary(long diary) {
        diaryPort.softDelete(diary);
    }

    public RetrieveDiariesResponse retrieveDiariesByTerm(long memberId, LocalDate startDate, LocalDate endDate) {
        val remindDuration = smeemProperties.getDuration().remind();
        return RetrieveDiariesResponse.of(
                diaryPort.findByMemberAndTerm(memberId, startDate, endDate),
                diaryPort.isExistByMemberAndPastAgo(memberId, remindDuration));
    }

    @Transactional
    public void deleteExpiredDiaries(int duration) {
        val expiredDate = LocalDate.now().minusDays(duration - 1);
        diaryPort.deleteByCreatedAt(expiredDate);
    }

    //TODO: 코칭 ERD 추가 설계 반영
    @Transactional
    public CoachDiaryResponse coachDiary(String content) {
        try {
            String message = getCoachPrompt(content);
            String openAiResponse = openAiPort.prompt(message)
                    .replace("```json", "")
                    .replace("```", "");

            List<Correction> corrections = objectMapper.readValue(openAiResponse, new TypeReference<>() {
            });

            return CoachDiaryResponse.of(corrections);
        } catch (JsonProcessingException exception) {
            throw new SmeemException(ExceptionCode.JSON_PARSE_ERROR, exception.getMessage());
        }
    }

    private String getCoachPrompt(String content) {
        int startIndex = 0;
        int endIndex = content.length() - 1;

        return String.format("""
        Paragraph : %s
        Start Index : %d
        End Index : %d

        JSON response description:
        - original_sentence: Separate sentence from paragraph.
        - corrected_sentence: corrected sentence including correct expressions.
        - reason: this value MUST be Korean. reason is why the sentence is corrected.
        """, content, startIndex, endIndex);
    }
}
