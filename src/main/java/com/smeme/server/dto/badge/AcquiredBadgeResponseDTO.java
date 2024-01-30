package com.smeme.server.dto.badge;

import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.BadgeType;
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
