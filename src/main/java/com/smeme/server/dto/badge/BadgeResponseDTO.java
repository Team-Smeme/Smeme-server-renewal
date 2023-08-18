package com.smeme.server.dto.badge;

import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.MemberBadge;

public record BadgeResponseDTO(
        Long id,
        String name,
        String type,
        String imageUrl
) {
    public static BadgeResponseDTO of(MemberBadge memberBadge) {
        return new BadgeResponseDTO(memberBadge.getBadge().getId(), memberBadge.getBadge().getName(), memberBadge.getBadge().getType().toString(), memberBadge.getBadge().getImageUrl());
    }

    public static BadgeResponseDTO of(Badge badge) {
        return new BadgeResponseDTO(badge.getId(), badge.getName(), badge.getType().toString(), badge.getImageUrl());
    }
}
