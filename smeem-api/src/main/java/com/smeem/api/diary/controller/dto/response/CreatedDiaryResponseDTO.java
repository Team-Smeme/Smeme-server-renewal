package com.smeem.api.diary.controller.dto.response;

import com.smeem.api.badge.controller.dto.response.AcquiredBadgeResponse;
import com.smeem.domain.badge.model.Badge;

import java.util.List;

public record CreatedDiaryResponseDTO(
        Long diaryId,
        List<AcquiredBadgeResponse> badges
) {
    public static CreatedDiaryResponseDTO of(Long diaryId, List<Badge> badges) {
        return new CreatedDiaryResponseDTO(diaryId, badges.stream().map(AcquiredBadgeResponse::of).toList());
    }
}
