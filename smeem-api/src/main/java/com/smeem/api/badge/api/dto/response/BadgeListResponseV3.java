package com.smeem.api.badge.api.dto.response;

import com.smeem.api.badge.service.dto.response.v3.BadgeListServiceResponseV3;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;


@Builder(access = PRIVATE)
public record BadgeListResponseV3(
        BadgeListServiceResponseV3 badges
) {

    public static BadgeListResponseV3 from(BadgeListServiceResponseV3 badges) {
        return BadgeListResponseV3.builder()
                .badges(badges)
                .build();
    }
}
