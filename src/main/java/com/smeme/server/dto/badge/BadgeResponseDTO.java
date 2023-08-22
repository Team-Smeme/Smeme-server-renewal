package com.smeme.server.dto.badge;

import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.BadgeType;
import com.smeme.server.model.badge.MemberBadge;

import lombok.Builder;

@Builder
public record BadgeResponseDTO(
	Long id,
	String name,
	BadgeType type,
	String imageUrl
) {
    public static BadgeResponseDTO of(MemberBadge memberBadge) {
        return BadgeResponseDTO.builder()
            .id(memberBadge.getBadge().getId())
            .name(memberBadge.getBadge().getName())
            .type(memberBadge.getBadge().getType())
            .imageUrl(memberBadge.getBadge().getImageUrl())
            .build();
    }

	public static BadgeResponseDTO of(Badge badge) {
        return BadgeResponseDTO.builder()
            .id(badge.getId())
            .name(badge.getName())
            .type(badge.getType())
            .imageUrl(badge.getImageUrl())
            .build();
	}
}
