package com.smeem.http.controller.dto.response.badge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record RetrieveMemberBadgesResponse(
        @Schema(description = "배지 정보")
        List<BadgeResponse> badges
) {

    @Builder(access = AccessLevel.PRIVATE)
    private record BadgeResponse(
            @Schema(description = "배지 id")
            long badgeId,
            @Schema(description = "배지 이름")
            String name,
            @Schema(description = "배지 유형")
            String type, //TODO: BadgeType
            @Schema(description = "획득 유무")
            boolean hasBadge,
            @Schema(description = "획득까지 남은 개수")
            Integer remainingNumber,
            @Schema(description = "미획득 메시지")
            String contentForNonBadgeOwner,
            @Schema(description = "획득 메시지")
            String contentForBadgeOwner,
            @Schema(description = "배지 이미지 url")
            String imageUrl,
            @Schema(description = "배지 획득 비율")
            float badgeAcquisitionRatio
    ) {
    }
}
