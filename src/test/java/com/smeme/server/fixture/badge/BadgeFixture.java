package com.smeme.server.fixture.badge;

import com.smeme.server.dto.badge.BadgeListResponseDTO;
import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.model.badge.BadgeType;

import java.util.List;
import java.util.stream.Stream;

public class BadgeFixture {

    private static final String BADGE_NAME = "연속 3일 일기 뱃지";
    private static final Long BADGE_ID = 1L;
    private static final BadgeType BADGE_TYPE = BadgeType.COMBO;
    private static final String BADGE_IMAGE_URL = "https://m.s3.ap-northeast-2.amazonaws.com/badge/streak.png";

    public static BadgeResponseDTO createBadgeResponseDTO() {
        return BadgeResponseDTO.builder()
                .id(BADGE_ID)
                .name(BADGE_NAME)
                .type(BADGE_TYPE.toString())
                .imageUrl(BADGE_IMAGE_URL)
                .build();
    }

    public static BadgeListResponseDTO createBadgeListResponseDTO() {
        return new BadgeListResponseDTO(createBadgeResponses());
    }

    private static List<BadgeListResponseDTO.BadgeResponseDTO> createBadgeResponses() {
        return Stream.iterate(1, i -> i + 1).limit(5)
                .map(BadgeFixture::createBadgeResponse)
                .toList();
    }

    private static BadgeListResponseDTO.BadgeResponseDTO createBadgeResponse(int i) {
        return BadgeListResponseDTO.BadgeResponseDTO.builder()
                .name(BADGE_NAME + i)
                .type(BADGE_TYPE)
                .imageUrl("https://...")
                .build();
    }

}
