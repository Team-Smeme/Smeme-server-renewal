package com.smeem.api.diary.controller.dto.response;

import com.smeem.api.badge.controller.dto.response.AcquiredBadgeResponse;
import com.smeem.api.badge.service.dto.response.AcquiredBadgeServiceResponse;
import com.smeem.api.badge.service.dto.response.BadgeServiceResponse;
import com.smeem.api.diary.service.dto.response.DiaryCreateServiceResponse;
import com.smeem.domain.badge.model.Badge;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record CreatedDiaryResponse(
        long diaryId,
        List<AcquiredBadgeResponse> badges
) {
    public static CreatedDiaryResponse of(DiaryCreateServiceResponse response) {
        return CreatedDiaryResponse.builder()
                .diaryId(response.diaryId())
                .badges(response.badges().stream().map(AcquiredBadgeResponse::of).toList())
                .build();
    }
}
