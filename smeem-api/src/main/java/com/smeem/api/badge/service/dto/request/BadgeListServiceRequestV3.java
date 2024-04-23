package com.smeem.api.badge.service.dto.request;


import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record BadgeListServiceRequestV3(
        long memberId
) {
    public static BadgeListServiceRequestV3 of(long memberId) {
        return BadgeListServiceRequestV3.builder()
                .memberId(memberId)
                .build();
    }
}
