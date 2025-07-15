package com.smeem.application.port.input.bookmark.dto;

import com.smeem.application.domain.bookmark.model.Bookmark;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record BookmarkDetailResponse(
        @Schema(description = "썸네일 이미지 url", example = "https://shorturl.at/0ToBd")
        String thumbnailImageUrl,

        @Schema(description = "스크랩 대상 url", example = "https://shorturl.at/YwTvv")
        String scrapedUrl,

        @Schema(description = "회화 표현", example = "can you make up our room?")
        String expression,

        @Schema(description = "회화 표현 뜻", example = "방 청소해주세요")
        String translatedExpression,

        @Schema(description = "스크랩 본문 내용", example = "오늘의 문장 ✅ Can you make up clean our room please?")
        String description
) {

    public static BookmarkDetailResponse from(Bookmark bookmark) {
        return BookmarkDetailResponse.builder()
                .thumbnailImageUrl(bookmark.getThumbnailImageUrl())
                .scrapedUrl(bookmark.getScrapedUrl())
                .expression(bookmark.getExpression())
                .translatedExpression(bookmark.getTranslatedExpression())
                .description(bookmark.getDescription())
                .build();
    }
}
