package com.smeem.application.port.input.bookmark.dto;

import com.smeem.application.domain.bookmark.model.Bookmark;
import com.smeem.application.domain.bookmark.model.ScrapType;
import com.smeem.application.port.output.web.scrap.ScrapInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record ScrapResponse(
        @Schema(description = "썸네일 이미지 url", example = "https://www.smeem.com")
        String thumbnail,

        @Schema(description = "요청한 url", example = "https://www.smeem.com")
        @NonNull
        String url,

        @Schema(description = "본문 내용", example = "1. Pissed off: 엄청 화나다 ...")
        String description,

        @Schema(description = "스크랩 컨텐츠 타입", example = "REELS")
        ScrapType scrapType
) {
        public static ScrapResponse from(ScrapInfo scrapInfo) {
                return ScrapResponse.builder()
                        .thumbnail(scrapInfo.image())
                        .url(scrapInfo.url())
                        .description(scrapInfo.description())
                        .build();
        }

        public static ScrapResponse from(Bookmark bookmark) {
                return ScrapResponse.builder()
                        .thumbnail(bookmark.getThumbnailImageUrl())
                        .url(bookmark.getScrapedUrl())
                        .description(bookmark.getDescription())
                        .scrapType(bookmark.getScrapType())
                        .build();
        }
}
