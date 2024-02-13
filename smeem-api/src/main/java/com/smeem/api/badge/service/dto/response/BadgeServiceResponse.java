package com.smeem.api.badge.service.dto.response;

import com.smeem.domain.badge.model.Badge;
import lombok.Builder;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record BadgeServiceResponse(
        Long id,
        String name,
        String type,
        String imageUrl
) {

    public static BadgeServiceResponse of(Badge badge) {
        return BadgeServiceResponse.builder()
                .id(badge.getId())
                .name(badge.getName())
                .type(badge.getType().name())
                .imageUrl(badge.getBadgeImage().getImageUrl())
                .build();
    }
}
