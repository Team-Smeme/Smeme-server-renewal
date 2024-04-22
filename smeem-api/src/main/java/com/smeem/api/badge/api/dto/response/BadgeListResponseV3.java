package com.smeem.api.badge.api.dto.response;

import com.smeem.api.badge.service.dto.response.v3.BadgeListServiceResponseV3;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;


@Builder(access = PRIVATE)
public record BadgeListResponseV3(
        List<BadgeResponseV3> badges
) {

    public static BadgeListResponseV3 from(BadgeListServiceResponseV3 badges) {
        return BadgeListResponseV3.builder()
                .badges(badges.badgeGetServiceResponseV3List().stream()
                        .map(BadgeResponseV3::from)
                        .toList())
                .build();
    }
}
