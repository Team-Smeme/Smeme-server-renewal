package com.smeem.application.port.input.bookmark.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

public record BookmarkRequest(
        @Schema(description = "스크랩 대상 url", example = "https://www.smeem.com")
        @NonNull String url
) {
}
