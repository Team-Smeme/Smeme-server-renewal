package com.smeem.application.domain.diary;

import com.smeem.application.config.SmeemProperties;
import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.badge.BadgeType;
import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.topic.Topic;
import com.smeem.application.port.input.DiaryUseCase;
import com.smeem.application.port.input.dto.request.diary.WriteDiaryRequest;
import com.smeem.application.port.input.dto.response.diary.RetrieveDiariesResponse;
import com.smeem.application.port.input.dto.response.diary.RetrieveDiaryResponse;
import com.smeem.application.port.input.dto.response.diary.WriteDiaryResponse;
import com.smeem.application.port.output.persistence.*;
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
    private final CorrectionPort correctionPort;
    private final DiaryPort diaryPort;
    private final MemberPort memberPort;
    private final BadgePort badgePort;
    private final MemberBadgePort memberBadgePort;
    private final TopicPort topicPort;
    private final SmeemProperties smeemProperties;

    @Transactional
    public WriteDiaryResponse writeDiary(long memberId, WriteDiaryRequest request) {
        Member member = memberPort.findById(memberId);
        if (request.topicId() != null) {
            topicPort.checkValidation(request.topicId());
        }
        Diary savedDiary = diaryPort.save(request.toDomain(member));

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
        Diary diary = diaryPort.findById(diaryId);
        Topic topic = diary.getTopicId() != null ? topicPort.findById(diary.getTopicId()) : null;
        Member member = memberPort.findById(diary.getMemberId());
        List<Correction> corrections = correctionPort.findByDiary(diaryId);
        return RetrieveDiaryResponse.of(diary, topic, member, corrections);
    }

    @Transactional
    public void modifyDiary(long memberId, long diaryId, WriteDiaryRequest request) {
        Diary foundDiary = diaryPort.findById(diaryId);
        foundDiary.validateDiaryOwnership(memberId);
        diaryPort.update(request.toDomain(foundDiary));
        correctionPort.deleteByDiary(diaryId);
    }

    @Transactional
    public void deleteDiary(long diaryId) {
        diaryPort.softDelete(diaryId);
        correctionPort.deleteByDiary(diaryId);
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
}
