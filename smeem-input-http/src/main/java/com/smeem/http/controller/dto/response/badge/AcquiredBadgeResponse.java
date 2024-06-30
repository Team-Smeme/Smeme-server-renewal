package com.smeem.http.controller.dto.response.badge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AcquiredBadgeResponse(
        @Schema(description = "배지 이름")
        String name,
        @Schema(description = "배지 이미지 url")
        String imageUrl,
        @Schema(description = "배지 종류")
        String type
) {
}
