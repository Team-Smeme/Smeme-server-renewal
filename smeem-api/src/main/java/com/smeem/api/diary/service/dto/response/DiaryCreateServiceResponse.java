package com.smeem.api.diary.service.dto.response;

import com.smeem.api.badge.service.dto.response.AcquiredBadgeServiceResponse;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.diary.model.Diary;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiaryCreateServiceResponse(
        long diaryId,
        List<AcquiredBadgeServiceResponse> badges
) {

    public static DiaryCreateServiceResponse of(Diary diary, List<Badge> badges) {
        return DiaryCreateServiceResponse.builder()
                .diaryId(diary.getId())
                .badges(badges.stream().map(AcquiredBadgeServiceResponse::of).toList())
                .build();
    }
}
