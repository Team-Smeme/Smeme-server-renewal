package com.smeem.api.diary.service.dto.request;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiaryGetServiceRequest(
        long diaryId
) {

    public static DiaryGetServiceRequest of(long diaryId) {
        return DiaryGetServiceRequest.builder()
                .diaryId(diaryId)
                .build();
    }
}
