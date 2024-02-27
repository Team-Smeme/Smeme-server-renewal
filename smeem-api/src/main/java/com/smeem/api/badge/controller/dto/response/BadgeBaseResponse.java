package com.smeem.api.badge.controller.dto.response;

import com.smeem.api.badge.service.dto.response.BadgeBaseServiceResponse;
import com.smeem.domain.badge.model.BadgeType;
import lombok.AccessLevel;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record BadgeBaseResponse(
        String name,
        BadgeType type,
        String imageUrl
) {

    public static BadgeBaseResponse from(BadgeBaseServiceResponse response) {
        return BadgeBaseResponse.builder()
                .name(response.name())
                .imageUrl(response.imageUrl())
                .type(response.type())
                .build();
    }
}
