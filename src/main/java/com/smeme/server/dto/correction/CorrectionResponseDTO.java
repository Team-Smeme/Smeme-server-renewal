package com.smeme.server.dto.correction;

import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.BadgeType;

public record CorrectionResponseDTO(Long diaryId, BadgeVO badge) {
	public static CorrectionResponseDTO of(Long diaryId, Badge badge) {
		return new CorrectionResponseDTO(diaryId, BadgeVO.of(badge));
	}

	public static CorrectionResponseDTO testOf() {
		return new CorrectionResponseDTO(1L, BadgeVO.testOf());
	}
}

record BadgeVO(
	Long id,
	BadgeType type,
	String name,
	String imageUrl
) {
	public static BadgeVO of(Badge badge) {
		return new BadgeVO(badge.getId(), badge.getType(), badge.getName(), badge.getImageUrl());
	}

	public static BadgeVO testOf() {
		return new BadgeVO(1L, BadgeType.EVENT, "웰컴 뱃지", "image url");
	}

}
