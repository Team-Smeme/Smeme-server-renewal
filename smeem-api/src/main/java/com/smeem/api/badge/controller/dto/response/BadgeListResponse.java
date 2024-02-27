package com.smeem.api.badge.controller.dto.response;

import lombok.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record BadgeListResponse(
        List<BadgeTypeResponse> badgeTypes
) {
    public static BadgeListResponse from(
            List<BadgeTypeResponse> response
    ) {
        return BadgeListResponse.builder()
                .badgeTypes(response)
                .build();
    }
}