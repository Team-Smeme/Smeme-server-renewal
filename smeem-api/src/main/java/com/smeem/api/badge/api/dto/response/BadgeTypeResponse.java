package com.smeem.api.badge.api.dto.response;

import com.smeem.api.badge.service.dto.response.BadgeTypeServiceResponse;
import com.smeem.domain.badge.model.BadgeType;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record BadgeTypeResponse(
        BadgeType badgeType,
        String badgeTypeName,
        List<BadgeBaseResponse> badges
) {
    public static BadgeTypeResponse from(BadgeTypeServiceResponse response) {
        return BadgeTypeResponse.builder()
                .badgeType(response.badgeType())
                .badgeTypeName(response.badgeTypeName())
                .badges(response.badges()
                        .stream()
                        .map(BadgeBaseResponse::from).toList())
                .build();
    }
}