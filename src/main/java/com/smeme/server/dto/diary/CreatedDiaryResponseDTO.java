package com.smeme.server.dto.diary;

import java.util.List;

import com.smeme.server.model.badge.Badge;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "일기 생성 응답 DTO")
public record CreatedDiaryResponseDTO(
	Long diaryId,
	List<BadgeDTO> badges
) {
	public static CreatedDiaryResponseDTO of(Long diaryId, List<Badge> badges) {
		return new CreatedDiaryResponseDTO(diaryId, badges.stream().map(BadgeDTO::of).toList());
	}
}

record BadgeDTO(
	String name,
	String imageUrl
) {
	public static BadgeDTO of(Badge badge) {
		return new BadgeDTO(badge.getName(), badge.getImageUrl());
	}
}
