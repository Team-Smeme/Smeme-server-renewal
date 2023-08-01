package com.smeme.server.dto.badge;

import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.MemberBadge;
import io.swagger.v3.oas.annotations.media.Schema;

public record BadgeResponseDTO(
        @Schema(description = "뱃지 id", example = "1")
        Long id,
        @Schema(description = "뱃지 이름", example = "연속 3일 일기 뱃지")
        String name,
        @Schema(description = "뱃지 타입", example = "STREAK")
        String type,
        @Schema(description = "뱃지 이미지 url", example = "https://m.s3.ap-northeast-2.amazonaws.com/badge/streak.png")
        String imageUrl) {
    public static BadgeResponseDTO of(MemberBadge memberBadge) {
        return new BadgeResponseDTO(memberBadge.getBadge().getId(), memberBadge.getBadge().getName(), memberBadge.getBadge().getType().toString(), memberBadge.getBadge().getImageUrl());
    }

    public static BadgeResponseDTO of(Badge badge) {
        return new BadgeResponseDTO(badge.getId(), badge.getName(), badge.getType().toString(), badge.getImageUrl());
    }
}
