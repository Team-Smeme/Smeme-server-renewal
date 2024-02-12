package com.smeem.api.diary.service.dto.response;

import com.smeem.api.badge.controller.dto.response.AcquiredBadgeResponseDTO;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.diary.model.Diary;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiaryCreateServiceResponse(
        long diaryId,
        List<AcquiredBadgeResponseDTO> badges
) {
    public static DiaryCreateServiceResponse of(Diary diary, List<Badge> badges) {
        return DiaryCreateServiceResponse.builder()
                .diaryId(diary.getId())
                .badges(badges.stream().map(AcquiredBadgeResponseDTO::of).toList())
                .build();
    }
}
