package com.smeme.server.dto.correction;

import com.smeme.server.model.badge.Badge;

public record CorrectionResponseDTO(Long diaryId, Badge badge) {
}
