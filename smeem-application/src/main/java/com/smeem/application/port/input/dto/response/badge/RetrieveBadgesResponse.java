package com.smeem.application.port.input.dto.response.badge;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.badge.BadgeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.Map;

@Builder(access = AccessLevel.PRIVATE)
public record RetrieveBadgesResponse(
        @Schema(description = "배지 종류 정보")
        List<BadgeTypeResponse> badgeTypes
) {

    public static RetrieveBadgesResponse from(Map<BadgeType, List<Badge>> typeToBadges, List<Long> acquiredBadgeIds) {
        return RetrieveBadgesResponse.builder()
                .badgeTypes(typeToBadges.keySet().stream()
                        .map(key -> BadgeTypeResponse.from(key, typeToBadges.get(key), acquiredBadgeIds))
                        .toList())
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private record BadgeTypeResponse(
            @Schema(description = "배지 종류")
            BadgeType badgeType,
            @Schema(description = "배지 종류 이름")
            String badgeTypeName,
            @Schema(description = "배지 정보")
            List<BadgeResponse> badges
    ) {

        private static BadgeTypeResponse from(BadgeType badgeType, List<Badge> badges, List<Long> acquiredBadgeIds) {
            return BadgeTypeResponse.builder()
                    .badgeType(badgeType)
                    .badgeTypeName(badgeType.getDescription())
                    .badges(badges.stream().map(badge -> BadgeResponse.from(badge, acquiredBadgeIds)).toList())
                    .build();
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    private record BadgeResponse(
            @Schema(description = "배지 이름")
            String name,
            @Schema(description = "배지 종류")
            BadgeType type,
            @Schema(description = "배지 이미지 url : 획득한 배지 이미지 컬러, 미획득한 배지 이미지 흑백")
            String imageUrl
    ) {

        private static BadgeResponse from(Badge badge, List<Long> acquiredBadgeIds) {
            return BadgeResponse.builder()
                    .name(badge.getName())
                    .type(badge.getBadgeType())
                    .imageUrl(getImageUrlByAcquired(badge, acquiredBadgeIds))
                    .build();
        }

        private static String getImageUrlByAcquired(Badge badge, List<Long> acquiredBadgeIds) {
            return acquiredBadgeIds.contains(badge.getId()) ? badge.getColorImageUrl() : badge.getGrayImageUrl();
        }
    }
}
