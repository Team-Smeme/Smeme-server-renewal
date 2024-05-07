package com.smeem.api.diary.service;

import com.smeem.api.badge.service.BadgeService;
import com.smeem.api.diary.service.dto.request.DiaryDeleteServiceRequest;
import com.smeem.api.diary.service.dto.request.DiaryModifyServiceRequest;
import com.smeem.api.diary.service.dto.request.DiaryCreateServiceRequest;
import com.smeem.api.diary.service.dto.response.DiaryCreateServiceResponse;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.diary.adapter.DiaryDeleter;
import com.smeem.domain.diary.adapter.DiaryFinder;
import com.smeem.domain.diary.adapter.DiarySaver;
import com.smeem.domain.member.adapter.member.MemberFinder;
import com.smeem.domain.topic.adapter.TopicFinder;
import com.smeem.domain.diary.model.Diary;
import com.smeem.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryCommandService {

    private final DiarySaver diarySaver;
    private final DiaryFinder diaryFinder;
    private final DiaryDeleter diaryDeleter;
    private final MemberFinder memberFinder;
    private final TopicFinder topicFinder;

    private final BadgeService badgeService;

    public DiaryCreateServiceResponse createDiary(final DiaryCreateServiceRequest request) {
        val member = memberFinder.findMemberCanWriteDiaryById(request.memberId());
        val diary = diarySaver.save(getDiary(request, member));
        member.updateDiaryComboCount();
        val badges = createMemberBadgeByCreatingDiary(member);
        return DiaryCreateServiceResponse.of(diary, badges);
    }

    public void modifyDiary(final DiaryModifyServiceRequest request) {
        val diary = diaryFinder.findById(request.diaryId());
        diary.updateContent(request.content());
    }

    public void deleteDiary(final DiaryDeleteServiceRequest request) {
        val diary = diaryFinder.findById(request.diaryId());
        diaryDeleter.softDelete(diary);
    }

    private Diary getDiary(final DiaryCreateServiceRequest request, Member member) {
        val diaryBuilder = Diary.builder()
                .content(request.content())
                .member(member);
        val topicId = request.topicId();
        if (nonNull(topicId)) {
            val topic = topicFinder.findById(topicId);
            diaryBuilder.topic(topic);
        }
        return diaryBuilder.build();
    }

    private List<Badge> createMemberBadgeByCreatingDiary(Member member) {
        val badges = new ArrayList<Badge>();
        badgeService.createCountingMemberBadge(member).ifPresent(badges::add);
        badgeService.createComboMemberBadge(member).ifPresent(badges::add);
        return badges;
    }
}
