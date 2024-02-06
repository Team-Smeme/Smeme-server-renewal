package com.smeem.api.badge.controller.dto.response;

import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.model.BadgeType;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AcquiredBadgeResponseDTO(
        String name,
        String imageUrl,
        BadgeType type
) {
    public static AcquiredBadgeResponseDTO of(Badge badge) {
        return AcquiredBadgeResponseDTO.builder()
                .name(badge.getName())
                .imageUrl(badge.getImageUrl())
                .type(badge.getType())
                .build();
    }
}
