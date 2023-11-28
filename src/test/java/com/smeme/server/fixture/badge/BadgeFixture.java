package com.smeme.server.fixture.badge;


import com.smeme.server.dto.badge.BadgeListResponseDTO;
import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.BadgeType;

import java.util.ArrayList;
import java.util.List;

public class BadgeFixture {

    private static final String BADGE_NAME = "연속 3일 일기 뱃지";
    private static final Long BADGE_ID = 1L;
    private static final BadgeType BADGE_TYPE = BadgeType.COMBO;
    private static final String BADGE_IMAGE_URL = "https://m.s3.ap-northeast-2.amazonaws.com/badge/streak.png";

    public static Badge createBadge() {
        return Badge.builder()
                .name(BADGE_NAME)
                .id(BADGE_ID)
                .type(BADGE_TYPE)
                .imageUrl(BADGE_IMAGE_URL)
                .build();
    }

    public static BadgeResponseDTO createBadgeResponseDTO() {
        return BadgeResponseDTO.of(createBadge());
    }

    public static BadgeListResponseDTO createBadgeListResponseDTO() {
        List<BadgeResponseDTO> badges = new ArrayList<>();

        for (long l = 1L; l < 3L; l++) {
            badges.add(
                    BadgeResponseDTO.of(
                            Badge.builder()
                                    .id(l)
                                    .name(BADGE_NAME)
                                    .type(BADGE_TYPE)
                                    .imageUrl(BADGE_IMAGE_URL)
                                    .build()
                    )
            );
        }
        return BadgeListResponseDTO.of(badges);
    }

}
