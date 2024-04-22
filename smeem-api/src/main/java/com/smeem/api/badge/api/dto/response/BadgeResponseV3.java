package com.smeem.api.badge.api.dto.response;

import com.smeem.api.badge.service.dto.response.v3.BadgeGetServiceResponseV3;
import com.smeem.domain.badge.model.BadgeType;

public record BadgeResponseV3(
        long badgeId,
        String name,
        BadgeType type,
        boolean hasBadge,
        Integer remainingNumber,
        String contentForNonBadgeOwner,
        String contentForBadgeOwner,
        String imageUrl,
        float badgeAcquisitionRatio
) {
    public static BadgeResponseV3 from(BadgeGetServiceResponseV3 badge) {
        return new BadgeResponseV3(
                badge.badgeId(),
                badge.name(),
                badge.type(),
                badge.hasBadge(),
                badge.remainingNumber(),
                badge.contentForNonBadgeOwner(),
                badge.contentForBadgeOwner(),
                badge.imageUrl(),
                badge.badgeAcquisitionRatio()
        );
    }
}
