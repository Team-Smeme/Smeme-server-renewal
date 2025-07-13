package com.smeem.application.port.input.dto.request.bookmark;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

public record BookmarkFallbackRequest(
        @Schema(description = "표현식", example = "Good Luck")
        @NonNull
        String expression,

        @Schema(description = "번역된 표현식", example = "굿럭")
        @NonNull
        String translatedExpression,

        @Schema(description = "썸네일 이미지 url", example = "https://...", nullable = true)
        @NonNull
        String thumbnailImageUrl,

        @Schema(description = "스크랩 할 url", example = "https://...")
        @NonNull
        String scrapedUrl,

        @Schema(description = "스크랩 본문", example = "hi")
        @NonNull
        String description
) {
}
