package com.smeem.api.diary.api.dto.response;

import com.smeem.api.diary.service.dto.response.DiaryGetServiceResponse;
import com.smeem.common.util.Util;
import lombok.Builder;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiaryGetResponse(
        long diaryId,
        String topic,
        String content,
        String createdAt,
        String username
) {

    public static DiaryGetResponse from(DiaryGetServiceResponse response) {
        return DiaryGetResponse.builder()
                .diaryId(response.diaryId())
                .topic(response.topicContent())
                .content(response.content())
                .createdAt(getCreatedAtToString(response.createdAt()))
                .username(response.username())
                .build();
    }

    private static String getCreatedAtToString(LocalDateTime createdAt) {
        return Util.transferToLocalDateTime(createdAt);
    }
}

