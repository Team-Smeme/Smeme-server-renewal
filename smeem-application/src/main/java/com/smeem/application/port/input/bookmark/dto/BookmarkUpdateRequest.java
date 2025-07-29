package com.smeem.application.port.input.bookmark.dto;

import com.smeem.application.domain.bookmark.model.Bookmark;
import io.swagger.v3.oas.annotations.media.Schema;

public record BookmarkUpdateRequest(
        @Schema(description = "영어 표현")
        String expression,
        @Schema(description = "한국어 표현")
        String translatedExpression,
        @Schema(description = "본문")
        String description
) {
        public Bookmark update(Bookmark originBookmark) {
                return Bookmark.builder()
                        .id(originBookmark.getId())
                        .memberId(originBookmark.getMemberId())
                        .scrapedUrl(originBookmark.getScrapedUrl())
                        .thumbnailImageUrl(originBookmark.getThumbnailImageUrl())
                        .description(description)
                        .expression(expression)
                        .translatedExpression(translatedExpression)
                        .build();
        }
}
