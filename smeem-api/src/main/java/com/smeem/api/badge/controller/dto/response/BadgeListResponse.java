package com.smeem.api.badge.controller.dto.response;

import com.smeem.api.badge.service.dto.response.BadgeListServiceResponse;
import lombok.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record BadgeListResponse(
        List<BadgeTypeResponse> badgeTypes
) {
    public static BadgeListResponse from(
            BadgeListServiceResponse response
    ) {
        return BadgeListResponse.builder()
                .badgeTypes(response.badgeTypes().stream()
                        .map(BadgeTypeResponse::from).toList())
                .build();
    }
}