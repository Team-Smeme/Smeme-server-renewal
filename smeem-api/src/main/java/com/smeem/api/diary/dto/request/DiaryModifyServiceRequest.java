package com.smeem.api.diary.dto.request;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiaryModifyServiceRequest(
        long diaryId,
        String content
) {

    public static DiaryModifyServiceRequest of(long diaryId, DiaryModifyRequest request) {
        return DiaryModifyServiceRequest.builder()
                .diaryId(diaryId)
                .content(request.content())
                .build();
    }
}
