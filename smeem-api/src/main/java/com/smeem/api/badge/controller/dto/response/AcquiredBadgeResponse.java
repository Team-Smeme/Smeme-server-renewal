package com.smeem.api.badge.controller.dto.response;

import com.smeem.api.badge.service.dto.response.AcquiredBadgeServiceResponse;
import com.smeem.domain.badge.model.BadgeType;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AcquiredBadgeResponse(
        String name,
        String imageUrl,
        BadgeType type
) {
    public static AcquiredBadgeResponse of(AcquiredBadgeServiceResponse response) {
        return AcquiredBadgeResponse.builder()
                .name(response.name())
                .imageUrl(response.imageUrl())
                .type(response.type())
                .build();
    }
}
