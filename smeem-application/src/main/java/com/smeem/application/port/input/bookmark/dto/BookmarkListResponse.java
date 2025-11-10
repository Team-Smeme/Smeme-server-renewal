package com.smeem.application.port.input.bookmark.dto;

import com.smeem.application.domain.bookmark.model.Bookmark;
import com.smeem.application.domain.bookmark.model.ScrapType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BookmarkListResponse(
        @Schema(description = "북마크 목록")
        List<BookmarkResponse> bookmarks
) {

    public static BookmarkListResponse from(List<Bookmark> bookmarks) {
        return BookmarkListResponse.builder()
                .bookmarks(bookmarks.stream().map(BookmarkResponse::from).toList())
                .build();
    }

    @Builder
    private record BookmarkResponse(
            @Schema(description = "북마크 id", example = "1")
            long bookmarkId,

            @Schema(description = "썸네일 이미지 url", example = "https://shorturl.at/0ToBd")
            String thumbnailImageUrl,

            @Schema(description = "회화 표현", example = "can you make up our room?")
            String expression,

            @Schema(description = "스크랩 본문 내용", example = "오늘의 문장 ✅ Can you make up clean our room please?")
            String description,

            @Schema(description = "등록 일자", example = "2025-07-16T00:34:49.396436")
            LocalDateTime createdAt,

            @Schema(description = "스크랩 컨텐츠 타입", example = "REELS")
            ScrapType scrapType
    ) {

        private static BookmarkResponse from(Bookmark bookmark) {
            return BookmarkResponse.builder()
                    .bookmarkId(bookmark.getId())
                    .thumbnailImageUrl(bookmark.getThumbnailImageUrl())
                    .expression(bookmark.getExpression())
                    .description(bookmark.getDescription())
                    .createdAt(bookmark.getCreatedAt())
                    .scrapType(bookmark.getScrapType())
                    .build();
        }
    }
}
