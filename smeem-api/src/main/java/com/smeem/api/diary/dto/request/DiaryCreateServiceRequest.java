package com.smeem.api.diary.dto.request;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiaryCreateServiceRequest(
        long memberId,
        String content,
        Long topicId
) {

    public static DiaryCreateServiceRequest of(long memberId, DiaryCreateRequest request) {
        return DiaryCreateServiceRequest.builder()
                .memberId(memberId)
                .content(request.content())
                .topicId(request.topicId())
                .build();
    }
}
