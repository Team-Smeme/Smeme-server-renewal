package com.smeem.api.badge.service.dto.response;

import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.model.BadgeType;
import lombok.AccessLevel;
import lombok.Builder;


@Builder(access = AccessLevel.PRIVATE)
public record AcquiredBadgeServiceResponse(
        String name,
        String imageUrl,
        BadgeType type
) {

    public static AcquiredBadgeServiceResponse of(Badge badge) {
        return AcquiredBadgeServiceResponse.builder()
                .name(badge.getName())
                .imageUrl(badge.getBadgeImage().getImageUrl())
                .type(badge.getType())
                .build();
    }
}
