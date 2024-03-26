package com.smeem.api.diary.api.dto.response;

import com.smeem.api.diary.service.dto.response.DiaryListGetServiceResponse;
import com.smeem.api.diary.service.dto.response.DiaryListGetServiceResponse.DiaryServiceResponse;
import com.smeem.api.support.Util;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiaryListGetResponse(
        List<DiaryResponse> diaries,
        boolean has30Past
) {

    public static DiaryListGetResponse from(DiaryListGetServiceResponse response) {
        return DiaryListGetResponse.builder()
                .diaries(response.diaries().stream().map(DiaryResponse::from).toList())
                .has30Past(response.hasDiaryWrittenAgo())
                .build();
    }

    @Builder(access = PRIVATE)
    public record DiaryResponse(
            long diaryId,
            String content,
            String createdAt
    ) {

        public static DiaryResponse from(DiaryServiceResponse response) {
            return DiaryResponse.builder()
                    .diaryId(response.diaryId())
                    .content(response.content())
                    .createdAt(getCreatedAtToString(response.createdAt()))
                    .build();
        }

        private static String getCreatedAtToString(LocalDateTime createdAt) {
            return Util.transferToLocalDateTime(createdAt);
        }
    }
}


