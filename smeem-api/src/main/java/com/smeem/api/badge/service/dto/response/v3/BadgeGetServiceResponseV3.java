package com.smeem.api.badge.service.dto.response.v3;

import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.model.BadgeType;
import lombok.Builder;

import static lombok.AccessLevel.*;


@Builder(access = PRIVATE)
public record BadgeGetServiceResponseV3(
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

    public static BadgeGetServiceResponseV3 of(Badge badge, boolean hasTrue, Integer remainingNumber) {
        return BadgeGetServiceResponseV3.builder()
                .badgeId(badge.getId())
                .name(badge.getName())
                .type(badge.getType())
                .hasBadge(hasTrue)
                .remainingNumber(remainingNumber)
                .contentForBadgeOwner(badge.getMessageForBadgeOwner())
                .contentForNonBadgeOwner(badge.getMessageForNonBadgeOwner())
                .imageUrl(badge.getBadgeImage().getImageUrl())
                .badgeAcquisitionRatio(badge.getBadgeAcquisitionRatio())
                .build();
    }
}
