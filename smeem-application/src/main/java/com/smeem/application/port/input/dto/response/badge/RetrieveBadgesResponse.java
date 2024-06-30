package com.smeem.application.port.input.dto.response.badge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record RetrieveBadgesResponse(
        @Schema(description = "배지 종류 정보")
        List<BadgeTypeResponse> badgeTypes
) {

    @Builder(access = AccessLevel.PRIVATE)
    private record BadgeTypeResponse(
            @Schema(description = "배지 종류")
            String badgeType,
            @Schema(description = "배지 종류 이름")
            String badgeTypeName,
            @Schema(description = "배지 정보")
            List<BadgeResponse> badges
    ) {

    }

    @Builder(access = AccessLevel.PRIVATE)
    private record BadgeResponse(
            @Schema(description = "배지 이름")
            String name,
            @Schema(description = "배지 종류")
            String type,
            @Schema(description = "배지 이미지 url : 획득한 배지 이미지 컬러, 미획득한 배지 이미지 흑백")
            String imageUrl
    ) {

    }
}
