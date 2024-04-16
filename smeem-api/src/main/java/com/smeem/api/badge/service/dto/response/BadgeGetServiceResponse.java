package com.smeem.api.badge.service.dto.response;

import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.model.BadgeType;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record BadgeGetServiceResponse(
        Long badgeId,
        String name,
        BadgeType type,
        String content,
        String badgeImageUrl,
        String badgeAcquiredPercent
) {
    public static BadgeGetServiceResponse of(
            Badge badge,
            String badgeAcquiredPercent
    ) {
        return BadgeGetServiceResponse.builder()
                .badgeId(badge.getId())
                .name(badge.getName())
                .type(badge.getType())
                .content(badge.getContent())
                .badgeImageUrl(badge.getBadgeImage().getImageUrl())
                .badgeAcquiredPercent(badgeAcquiredPercent)
                .build();

    }
}
