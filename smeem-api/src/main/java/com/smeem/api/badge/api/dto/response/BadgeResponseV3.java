package com.smeem.api.badge.api.dto.response;

import com.smeem.api.badge.service.dto.response.v3.BadgeGetServiceResponseV3;
import com.smeem.domain.badge.model.BadgeType;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
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
        return BadgeResponseV3.builder()
                .badgeId(badge.badgeId())
                .name(badge.name())
                .type(badge.type())
                .hasBadge(badge.hasBadge())
                .remainingNumber(badge.remainingNumber())
                .contentForNonBadgeOwner(badge.contentForNonBadgeOwner())
                .contentForBadgeOwner(badge.contentForBadgeOwner())
                .imageUrl(badge.imageUrl())
                .badgeAcquisitionRatio(badge.badgeAcquisitionRatio())
                .build();
    }
}
