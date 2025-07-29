package com.smeem.application.port.input.bookmark.dto;

import com.smeem.application.domain.bookmark.model.Bookmark;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record BookmarkModifyResponse(
        @Schema(description = "수정된 회화 표현", example = "can you make up our room?")
        String expression,

        @Schema(description = "수정된 회화 표현 뜻", example = "방 청소해주세요")
        String translatedExpression,

        @Schema(description = "수정된 스크랩 본문 내용", example = "오늘의 문장 ✅ Can you make up clean our room please?")
        String description
) {

    public static BookmarkModifyResponse from(Bookmark bookmark) {
        return BookmarkModifyResponse.builder()
                .expression(bookmark.getExpression())
                .translatedExpression(bookmark.getTranslatedExpression())
                .description(bookmark.getDescription())
                .build();
    }
}
