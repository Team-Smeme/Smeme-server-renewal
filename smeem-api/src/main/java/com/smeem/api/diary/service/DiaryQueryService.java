package com.smeem.api.diary.service;

import com.smeem.api.diary.service.dto.response.DiaryListGetServiceResponse;
import com.smeem.api.diary.service.dto.response.DiaryGetServiceResponse;
import com.smeem.api.diary.service.dto.request.DiaryGetServiceRequest;
import com.smeem.api.diary.service.dto.request.DiaryListGetServiceRequest;
import com.smeem.common.config.ValueConfig;
import com.smeem.domain.diary.adapter.DiaryFinder;
import com.smeem.domain.member.adapter.member.MemberFinder;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryQueryService {

    private final DiaryFinder diaryFinder;
    private final MemberFinder memberFinder;

    private final ValueConfig valueConfig;

    public DiaryGetServiceResponse getDiaryDetail(final DiaryGetServiceRequest request) {
        val diary = diaryFinder.findById(request.diaryId());
        return DiaryGetServiceResponse.of(diary);
    }

    public DiaryListGetServiceResponse getDiaries(final DiaryListGetServiceRequest request) {
        val member = memberFinder.findById(request.memberId());
        val diaries = member.getDiariesBetweenDate(request.startDate(), request.endDate());
        return DiaryListGetServiceResponse.of(diaries, member, valueConfig);
    }
}
