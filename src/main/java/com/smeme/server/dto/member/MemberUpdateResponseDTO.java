package com.smeme.server.dto.member;

import com.smeme.server.model.badge.Badge;

import java.util.List;

public record MemberUpdateResponseDTO(
        List<BadgeDTO> badges
) {
    public static MemberUpdateResponseDTO of(List<Badge> badges) {
        return new MemberUpdateResponseDTO(badges.stream()
            .map(badge -> new BadgeDTO(badge.getName(), badge.getImageUrl())).toList());
    }

    public record BadgeDTO(
            String name,
            String imageUrl
    ) {
    }
}

