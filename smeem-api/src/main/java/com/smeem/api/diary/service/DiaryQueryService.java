package com.smeem.api.diary.service;

import com.smeem.api.diary.service.dto.response.DiaryListGetServiceResponse;
import com.smeem.api.diary.service.dto.response.DiaryGetServiceResponse;
import com.smeem.api.diary.service.dto.request.DiaryGetServiceRequest;
import com.smeem.api.diary.service.dto.request.DiaryListGetServiceRequest;
import com.smeem.common.config.ValueConfig;
import com.smeem.common.exception.DiaryException;
import com.smeem.common.exception.MemberException;
import com.smeem.domain.diary.model.Diary;
import com.smeem.domain.diary.repository.DiaryRepository;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.smeem.common.code.failure.DiaryFailureCode.INVALID_DIARY;
import static com.smeem.common.code.failure.MemberFailureCode.INVALID_MEMBER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryQueryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    private final ValueConfig valueConfig;

    public DiaryGetServiceResponse getDiaryDetail(final DiaryGetServiceRequest request) {
        val diary = findDiary(request.diaryId());
        return DiaryGetServiceResponse.of(diary);
    }

    public DiaryListGetServiceResponse getDiaries(final DiaryListGetServiceRequest request) {
        val member = findMember(request.memberId());
        val diaries = member.getDiariesBetweenDate(request.startDate(), request.endDate());
        return DiaryListGetServiceResponse.of(diaries, member, valueConfig);
    }

    private Diary findDiary(long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new DiaryException(INVALID_DIARY));
    }

    private Member findMember(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(INVALID_MEMBER));
    }
}
