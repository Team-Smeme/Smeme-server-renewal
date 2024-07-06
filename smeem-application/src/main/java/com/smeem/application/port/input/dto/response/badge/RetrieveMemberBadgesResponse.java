package com.smeem.application.port.input.dto.response.badge;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.badge.BadgeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder(access = AccessLevel.PRIVATE)
public record RetrieveMemberBadgesResponse(
        @Schema(description = "배지 정보")
        List<BadgeResponse> badges
) {

    public static RetrieveMemberBadgesResponse from(
            List<Badge> badges,
            List<Long> acquiredBadgeIds,
            Map<BadgeType, Integer> badgeTypeToPerformance
    ) {
        return RetrieveMemberBadgesResponse.builder()
                .badges(badges.stream()
                        .map(badge -> BadgeResponse.from(badge, acquiredBadgeIds, badgeTypeToPerformance))
                        .toList())
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private record BadgeResponse(
            @Schema(description = "배지 id")
            long badgeId,
            @Schema(description = "배지 이름")
            String name,
            @Schema(description = "배지 유형")
            BadgeType type,
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

        private static BadgeResponse from(
                Badge badge,
                List<Long> acquiredBadgeIds,
                Map<BadgeType, Integer> badgeTypeToPerformance
        ) {
            return BadgeResponse.builder()
                    .badgeId(badge.getId())
                    .name(badge.getName())
                    .type(badge.getBadgeType())
                    .hasBadge(acquiredBadgeIds.contains(badge.getId()))
                    .remainingNumber(calculateRemainingNumber(badge, badgeTypeToPerformance))
                    .contentForNonBadgeOwner(badge.getMessageNotAcquired())
                    .contentForBadgeOwner(badge.getMessageAcquired())
                    .imageUrl(badge.getColorImageUrl())
                    .badgeAcquisitionRatio(badge.getAcquisitionRate())
                    .build();
        }

        private static Integer calculateRemainingNumber(Badge badge, Map<BadgeType, Integer> badgeTypeToPerformance) {
            if (badgeTypeToPerformance.containsKey(badge.getBadgeType())) {
                return badge.getStandard() - badgeTypeToPerformance.get(badge.getBadgeType());
            }
            return null;
        }
    }
}
