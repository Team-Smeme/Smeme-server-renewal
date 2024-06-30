package com.smeem.application.port.input.dto.response.plan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record RetrieveMemberPlanResponse(
        @Schema(description = "학습계획")
        String plan,
        @Schema(description = "학습목표")
        String goal,
        @Schema(description = "성취한 횟수")
        int clearedCount,
        @Schema(description = "전체 성취 횟수")
        int clearCount
) {
}
