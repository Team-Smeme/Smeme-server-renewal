package com.smeem.diary.controller.dto.response;

import java.util.List;

import com.smeme.server.dto.badge.AcquiredBadgeResponseDTO;
import com.smeme.server.model.badge.Badge;

public record CreatedDiaryResponseDTO(
        Long diaryId,
        List<AcquiredBadgeResponseDTO> badges
) {
    public static CreatedDiaryResponseDTO of(Long diaryId, List<Badge> badges) {
        return new CreatedDiaryResponseDTO(diaryId, badges.stream().map(AcquiredBadgeResponseDTO::of).toList());
    }
}
