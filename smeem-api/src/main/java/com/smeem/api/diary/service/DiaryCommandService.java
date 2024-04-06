package com.smeem.api.diary.service;

import com.smeem.api.badge.service.BadgeService;
import com.smeem.api.diary.service.dto.request.DiaryDeleteServiceRequest;
import com.smeem.api.diary.service.dto.request.DiaryModifyServiceRequest;
import com.smeem.api.diary.service.dto.request.DiaryCreateServiceRequest;
import com.smeem.api.diary.service.dto.response.DiaryCreateServiceResponse;
import com.smeem.domain.diary.exception.DiaryException;
import com.smeem.domain.member.exception.MemberException;
import com.smeem.domain.topic.exception.TopicException;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.diary.model.DeletedDiary;
import com.smeem.domain.diary.model.Diary;
import com.smeem.domain.diary.repository.DeletedDiaryRepository;
import com.smeem.domain.diary.repository.DiaryRepository;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.domain.topic.model.Topic;
import com.smeem.domain.topic.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.smeem.common.code.failure.DiaryFailureCode.EXIST_TODAY_DIARY;
import static com.smeem.common.code.failure.DiaryFailureCode.INVALID_DIARY;
import static com.smeem.common.code.failure.MemberFailureCode.INVALID_MEMBER;
import static com.smeem.common.code.failure.TopicFailureCode.INVALID_TOPIC;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryCommandService {

    private final DiaryRepository diaryRepository;
    private final DeletedDiaryRepository deletedDiaryRepository;
    private final MemberRepository memberRepository;
    private final TopicRepository topicRepository;

    private final BadgeService badgeService;

    public DiaryCreateServiceResponse createDiary(final DiaryCreateServiceRequest request) {
        val member = getMemberCheckedNoDiaryWrittenToday(request.memberId());
        val savedDiary = saveDiary(request, member);
        val acquiredBadges = acquireBadgesByCreatingDiary(member, savedDiary);
        return DiaryCreateServiceResponse.of(savedDiary, acquiredBadges);
    }

    public void modifyDiary(final DiaryModifyServiceRequest request) {
        val diary = findDiary(request.diaryId());
        diary.updateContent(request.content());
    }

    public void deleteDiary(final DiaryDeleteServiceRequest request) {
        val diary = findDiary(request.diaryId());
        moveToDeletedDairy(diary);
        deleteDiary(diary);
    }

    public void deleteAllByMember(final Member member) {
        diaryRepository.deleteAllByMember(member);
        deletedDiaryRepository.deleteByMember(member);
    }

    private Member getMemberCheckedNoDiaryWrittenToday(long memberId) {
        val member = findMember(memberId);
        if (member.hasDiaryWrittenToday()) {
            throw new DiaryException(EXIST_TODAY_DIARY);
        }
        return member;
    }

    private Member findMember(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(INVALID_MEMBER));
    }

    private Diary saveDiary(final DiaryCreateServiceRequest request, Member member) {
        val diary = getDiaryByTopic(request, member);
        return diaryRepository.save(diary);
    }

    private Diary getDiaryByTopic(final DiaryCreateServiceRequest request, Member member) {
        val topicId = request.topicId();
        return Objects.isNull(topicId)
                ? getDiaryWithoutTopic(request, member)
                : getDiaryWithTopic(request, member);
    }

    private Diary getDiaryWithoutTopic(final DiaryCreateServiceRequest request, Member member) {
        return Diary.builder()
                .content(request.content())
                .member(member)
                .build();
    }

    private Diary getDiaryWithTopic(final DiaryCreateServiceRequest request, Member member) {
        val topic = findTopic(request.topicId());
        return Diary.builder()
                .content(request.content())
                .topic(topic)
                .member(member)
                .build();
    }

    private Topic findTopic(long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new TopicException(INVALID_TOPIC));
    }

    private List<Badge> acquireBadgesByCreatingDiary(final Member member, final Diary diary) {
        val acquiredBadge = acquireBadge(member, diary);
        acquiredBadge.forEach(badge -> badgeService.saveMemberBadge(member, badge));
        return acquiredBadge;
    }

    private List<Badge> acquireBadge(final Member member, final Diary diary) {
        val badges = new ArrayList<Badge>();
        acquireBadgeByCountOfDiary(member, badges);
        acquireBadgeByComboCountOfDiary(member, diary, badges);
        return badges;
    }

    private void acquireBadgeByCountOfDiary(final Member member, List<Badge> badges) {
        val countingBadge = badgeService.getBadgeByCountOfDiary(member.getDiaries().size());
        if (checkAcquiredBadge(member, countingBadge)) {
            badges.add(countingBadge);
        }
    }

    private void acquireBadgeByComboCountOfDiary(final Member member, final Diary diary, List<Badge> badges) {
        member.updateDiaryCombo(diary);
        val comboBadge = badgeService.getBadgeByComboCountOfDiary(member.getDiaryComboCount());
        if (checkAcquiredBadge(member, comboBadge)) {
            badges.add(comboBadge);
        }
    }

    private boolean checkAcquiredBadge(final Member member, final Badge badge) {
        return nonNull(badge) && member.hasNotBadge(badge);
    }

    private Diary findDiary(long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new DiaryException(INVALID_DIARY));
    }

    private void moveToDeletedDairy(final Diary diary) {
        deletedDiaryRepository.save(new DeletedDiary(diary));
    }

    private void deleteDiary(final Diary diary) {
        diary.deleteFromMember();
        diaryRepository.deleteById(diary.getId());
    }
}
