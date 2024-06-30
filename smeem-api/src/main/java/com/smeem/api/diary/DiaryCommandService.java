package com.smeem.api.domain.diary;

import com.smeem.api.diary.api.dto.request.DiaryCreateRequest;
import com.smeem.api.domain.badge.BadgeService;
import com.smeem.api.diary.service.dto.request.DiaryDeleteServiceRequest;
import com.smeem.api.diary.service.dto.request.DiaryModifyServiceRequest;
import com.smeem.api.diary.service.dto.request.DiaryCreateServiceRequest;
import com.smeem.api.diary.service.dto.response.DiaryCreateServiceResponse;
import com.smeem.api.domain.badge.vo.Badge;
import com.smeem.api.domain.diary.dto.response.DiaryWriteResponse;
import com.smeem.api.domain.diary.vo.Diary;
import com.smeem.api.domain.member.Member;
import com.smeem.api.domain.memberbadge.vo.MemberBadge;
import com.smeem.api.port.output.persistence.diary.DiaryFinder;
import com.smeem.api.port.output.persistence.diary.DiarySaver;
import com.smeem.api.port.output.persistence.member.MemberFinder;
import com.smeem.api.port.output.persistence.member.MemberUpdater;
import com.smeem.common.exception.SmeemException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class DiaryCommandService {

    private final DiarySaver diarySaver;
    private final DiaryFinder diaryFinder;
    private final MemberFinder memberFinder;
    private final MemberUpdater memberUpdater;

    private final BadgeService badgeService;

    public DiaryWriteResponse write(long memberId, DiaryCreateRequest request) {
        val member = memberFinder.findById(memberId);

        if (diaryFinder.isExistByMemberToday(member.id())) {
            throw new SmeemException(400, "금일 일기를 작성한 회원");
        }

        val diary = diarySaver.save(Diary.of(member, request));
        updateDiaryComboCount(member);
        val acquiredBadges = acquireBadgesByWriting(); //TODO: implementation

        return DiaryWriteResponse.from(diary, acquiredBadges);
    }

    private List<Badge> acquireBadgesByWriting(Member member) {

    }

    private Optional<Badge> acquireBadgeOfCounting() {

    }

    private Optional<Badge> acquireBadgeOfCombo() {

    }

    private List<Badge> createMemberBadgeByCreatingDiary(Member member) {
        val badges = new ArrayList<Badge>();
        badgeService.createCountingMemberBadge(member).ifPresent(badges::add);
        badgeService.createComboMemberBadge(member).ifPresent(badges::add);
        return badges;
    }

    public Optional<Badge> createCountingMemberBadge(final Member member) {
        val countingBadge = badgeFinder.findBadgeByDiaryCount(member.getDiaries().size());
        return createMemberBadge(member, countingBadge);
    }

    public Optional<Badge> createComboMemberBadge(final Member member) {
        val comboBadge = badgeFinder.findBadgeByDiaryComboCount(member.getDiaryComboInfo().getDiaryComboCount());
        return createMemberBadge(member, comboBadge);
    }

    private Optional<Badge> createMemberBadge(final Member member, final Optional<Badge> badge) {
        if (badge.isPresent() && member.hasNotBadge(badge.get())) {
            memberBadgeSaver.saveByMemberAndBadge(member, badge.get());
            return badge;
        }
        return Optional.empty();
    }

    private void updateDiaryComboCount(Member member) {
        if (diaryFinder.isExistByMemberAndYesterday(member.id())) {
            memberUpdater.updateDiaryComboCount(member.diaryComboCount() + 1);
        } else {
            memberUpdater.updateDiaryComboCount(1);
        }
    }

    public void modifyDiary(final DiaryModifyServiceRequest request) {
        val diary = diaryFinder.findById(request.diaryId());
        diary.updateContent(request.content());
    }

    public void deleteDiary(final DiaryDeleteServiceRequest request) {
        val diary = diaryFinder.findById(request.diaryId());
        diaryDeleter.softDelete(diary);
    }
}
