package com.smeme.server.dto.diary;

import java.util.List;

import com.smeme.server.model.badge.Badge;

public record CreatedDiaryResponseDTO(
	Long diaryId,
	List<BadgeDTO> badges
) {
	public static CreatedDiaryResponseDTO of(Long diaryId, List<Badge> badges) {
		return new CreatedDiaryResponseDTO(diaryId, badges.stream().map(BadgeDTO::of).toList());
	}

	public static CreatedDiaryResponseDTO testOf() {
		return new CreatedDiaryResponseDTO(1L, List.of(BadgeDTO.testOf()));
	}
}

record BadgeDTO(
	String name,
	String imageUrl
) {
	public static BadgeDTO of(Badge badge) {
		return new BadgeDTO(badge.getName(), badge.getImageUrl());
	}

	public static BadgeDTO testOf() {
		return new BadgeDTO("웰컴 뱃지", "image-url");
	}
}
