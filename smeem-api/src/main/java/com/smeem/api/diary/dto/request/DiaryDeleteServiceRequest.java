package com.smeem.api.diary.dto.request;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiaryDeleteServiceRequest(
        long diaryId
) {

    public static DiaryDeleteServiceRequest of(long diaryId) {
        return DiaryDeleteServiceRequest.builder()
                .diaryId(diaryId)
                .build();
    }
}
