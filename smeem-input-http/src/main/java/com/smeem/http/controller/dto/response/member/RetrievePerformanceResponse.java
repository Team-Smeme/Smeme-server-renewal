package com.smeem.http.controller.dto.response.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record RetrievePerformanceResponse(
        @Schema(description = "방문 횟수")
        int visitDays,
        @Schema(description = "작성한 일기 개수")
        int diaryCount,
        @Schema(description = "일기 연속작성 일수")
        int diaryComboCount,
        @Schema(description = "획득한 배지 개수")
        int badgeCount
) {
}
