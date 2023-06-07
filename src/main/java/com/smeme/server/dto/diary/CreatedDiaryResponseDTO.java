package com.smeme.server.dto.diary;

import java.util.List;
import com.smeme.server.model.badge.Badge;

public record CreatedDiaryResponseDTO(Long diaryId, List<BadgeDTO> badges) {
	public static CreatedDiaryResponseDTO of(Long diaryId, List<Badge> badges) {
		return new CreatedDiaryResponseDTO(diaryId, badges.stream().map(BadgeDTO::of).toList());
	}
}

record BadgeDTO(String name, String imageUrl) {
	public static BadgeDTO of(Badge badge) {
		return new BadgeDTO(badge.getName(), badge.getImageUrl());
	}
}
