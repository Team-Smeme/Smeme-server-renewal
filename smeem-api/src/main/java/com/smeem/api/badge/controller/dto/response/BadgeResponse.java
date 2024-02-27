package com.smeem.api.badge.controller.dto.response;

import com.smeem.api.badge.service.dto.response.BadgeServiceResponse;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record BadgeResponse(
        Long id,
        String name,
        String type,
        String imageUrl
) {

    public static BadgeResponse from(BadgeServiceResponse response) {
        return new BadgeResponse(
                response.id(),
                response.name(),
                response.type(),
                response.imageUrl()
        );
    }
}
