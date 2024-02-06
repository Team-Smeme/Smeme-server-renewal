package com.smeem.badge.controller.dto.response;

import com.smeme.server.model.badge.Badge;
import lombok.Builder;

@Builder
public record BadgeResponseDTO(
        Long id,
        String name,
        String type,
        String imageUrl
) {

    public static BadgeResponseDTO of(Badge badge) {
        return new BadgeResponseDTO(badge.getId(), badge.getName(), badge.getType().toString(), badge.getImageUrl());
    }
}
