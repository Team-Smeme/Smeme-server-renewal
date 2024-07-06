package com.smeem.application.port.input.dto.response.badge;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.badge.BadgeType;
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
        BadgeType type
) {

        public static AcquiredBadgeResponse from(Badge badge) {
                return AcquiredBadgeResponse.builder()
                        .name(badge.getName())
                        .imageUrl(badge.getColorImageUrl())
                        .type(badge.getBadgeType())
                        .build();
        }
}
