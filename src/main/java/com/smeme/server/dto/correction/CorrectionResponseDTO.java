package com.smeme.server.dto.correction;

import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.model.badge.Badge;

public record CorrectionResponseDTO(
	Long diaryId,
	BadgeResponseDTO badge
) {
	public static CorrectionResponseDTO of(Long diaryId, Badge badge) {
		return new CorrectionResponseDTO(diaryId, BadgeResponseDTO.of(badge));
	}
}
