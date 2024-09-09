package com.smeem.application.domain.diary;

import com.smeem.application.config.SmeemProperties;
import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.badge.BadgeType;
import com.smeem.application.domain.member.Member;
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
    private final DiaryPort diaryPort;
    private final MemberPort memberPort;
    private final BadgePort badgePort;
    private final MemberBadgePort memberBadgePort;
    private final TopicPort topicPort;
    private final SmeemProperties smeemProperties;

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
}
