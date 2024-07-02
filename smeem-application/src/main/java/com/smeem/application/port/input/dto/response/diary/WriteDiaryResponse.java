package com.smeem.application.port.input.dto.response.diary;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.diary.Diary;
import com.smeem.application.port.input.dto.response.badge.AcquiredBadgeResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record WriteDiaryResponse(
        @Schema(description = "일기 id")
        long diaryId,
        @Schema(description = "일기 작성 후 획득한 배지 정보")
        List<AcquiredBadgeResponse> badges
) {

        public static WriteDiaryResponse of(Diary diary, List<Badge> acquiredBadges) {
                return WriteDiaryResponse.builder()
                        .diaryId(diary.getId())
                        .badges(acquiredBadges.stream().map(AcquiredBadgeResponse::from).toList())
                        .build();
        }

}
