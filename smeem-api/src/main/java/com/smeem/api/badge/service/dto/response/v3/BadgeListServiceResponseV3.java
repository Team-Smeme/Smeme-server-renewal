package com.smeem.api.badge.service.dto.response.v3;

import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record BadgeListServiceResponseV3(
        List<BadgeGetServiceResponseV3> badgeGetServiceResponseV3List
) {

    public static BadgeListServiceResponseV3 of(List<BadgeGetServiceResponseV3> response) {
        return BadgeListServiceResponseV3.builder()
                .badgeGetServiceResponseV3List(response)
                .build();
    }
}
