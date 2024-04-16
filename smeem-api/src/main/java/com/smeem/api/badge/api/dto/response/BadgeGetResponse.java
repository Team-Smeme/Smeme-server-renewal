package com.smeem.api.badge.api.dto.response;

import com.smeem.api.badge.service.dto.response.BadgeGetServiceResponse;
import com.smeem.domain.badge.model.BadgeType;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;


@Builder(access = PRIVATE)
public record BadgeGetResponse(
        long badgeId,
        BadgeType type,
        String name,
        String content,
        String badgeImageUrl,
        String badgeAcquiredPercent
) {

    public static BadgeGetResponse from(BadgeGetServiceResponse response) {
        return BadgeGetResponse.builder()
                .badgeId(response.badgeId())
                .type(response.type())
                .name(response.name())
                .content(response.content())
                .badgeImageUrl(response.badgeImageUrl())
                .badgeAcquiredPercent(response.badgeAcquiredPercent())
                .build();

    }
}
