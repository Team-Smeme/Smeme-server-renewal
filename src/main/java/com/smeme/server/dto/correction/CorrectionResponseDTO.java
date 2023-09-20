package com.smeme.server.dto.correction;

import com.smeme.server.model.badge.Badge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record CorrectionResponseDTO(
        Long diaryId,
        List<BadgeDTO> badges
) {
    public static CorrectionResponseDTO of(Long diaryId, Badge badge) {
        return new CorrectionResponseDTO(diaryId, new ArrayList<>(Collections.singleton(BadgeDTO.of(badge))));
    }

    record BadgeDTO(
            String name,
            String imageUrl
    ) {
        public static BadgeDTO of(Badge badge) {
            return new BadgeDTO(badge.getName(), badge.getImageUrl());
        }
    }
}
