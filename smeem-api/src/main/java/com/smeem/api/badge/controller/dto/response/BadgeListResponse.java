package com.smeem.api.badge.controller.dto.response;

import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.model.BadgeType;
import com.smeem.domain.member.model.MemberBadge;
import lombok.*;

import java.util.List;
import java.util.Map;

@Builder(access = AccessLevel.PRIVATE)
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