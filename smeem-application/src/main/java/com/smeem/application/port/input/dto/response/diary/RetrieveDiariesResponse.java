package com.smeem.application.port.input.dto.response.diary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record RetrieveDiariesResponse(
        @Schema(description = "일기 정보")
        List<DiaryResponse> diaries,
        @Schema(description = "30일 전 작성한 일기의 존재 유무")
        boolean has30Past
) {

    @Builder(access = PRIVATE)
    public record DiaryResponse(
            @Schema(description = "일기 id")
            long diaryId,
            @Schema(description = "일기 내용")
            String content,
            @Schema(description = "일기 작성일")
            String createdAt
    ) {
    }
}


