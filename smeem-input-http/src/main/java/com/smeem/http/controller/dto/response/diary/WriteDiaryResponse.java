package com.smeem.http.controller.dto.response.diary;

import com.smeem.http.controller.dto.response.badge.AcquiredBadgeResponse;
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

}
