package com.smeem.application.domain.diary;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.badge.BadgeType;
import com.smeem.application.domain.member.Member;
import com.smeem.application.port.input.DiaryUseCase;
import com.smeem.application.port.input.dto.request.diary.WriteDiaryRequest;
import com.smeem.application.port.input.dto.response.diary.RetrieveDiariesResponse;
import com.smeem.application.port.input.dto.response.diary.RetrieveDiaryResponse;
import com.smeem.application.port.input.dto.response.diary.WriteDiaryResponse;
import com.smeem.application.port.output.persistence.BadgePort;
import com.smeem.application.port.output.persistence.DiaryPort;
import com.smeem.application.port.output.persistence.MemberBadgePort;
import com.smeem.application.port.output.persistence.MemberPort;
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

    @Transactional
    public WriteDiaryResponse writeDiary(long memberId, WriteDiaryRequest request) {
        val member = memberPort.findById(memberId);
        val savedDiary = diaryPort.save(request.toDomain(member));

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
                .toList();
    }

    public RetrieveDiaryResponse retrieveDiary(long diaryId) {
        return null;
    }

    @Transactional
    public void modifyDiary(long diaryId, WriteDiaryRequest request) {

    }

    @Transactional
    public void deleteDiary(long diary) {

    }

    public RetrieveDiariesResponse retrieveDiariesByTerm(long memberId, LocalDate startDate, LocalDate endDate) {
        return null;
    }
}
