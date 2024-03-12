package com.smeem.api.diary.api.dto.response;

import com.smeem.api.badge.controller.dto.response.AcquiredBadgeResponse;
import com.smeem.api.diary.service.dto.response.DiaryCreateServiceResponse;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiaryCreateResponse(
        long diaryId,
        List<AcquiredBadgeResponse> badges
) {

    public static DiaryCreateResponse from(DiaryCreateServiceResponse response) {
        return DiaryCreateResponse.builder()
                .diaryId(response.diaryId())
                .badges(response.badges().stream().map(AcquiredBadgeResponse::from).toList())
                .build();
    }
}
