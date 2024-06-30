package com.smeem.http.controller.dto.response.diary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record RetrieveDiaryResponse(
        @Schema(description = "일기 id")
        long diaryId,
        @Schema(description = "일기 주제")
        String topic,
        @Schema(description = "일기 내용")
        String content,
        @Schema(description = "일기 작성일")
        String createdAt,
        @Schema(description = "일기 작성자 닉네임")
        String username
) {

}

