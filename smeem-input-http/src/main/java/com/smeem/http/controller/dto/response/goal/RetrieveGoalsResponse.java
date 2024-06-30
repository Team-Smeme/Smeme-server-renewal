package com.smeem.http.controller.dto.response.goal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record RetrieveGoalsResponse(
        @Schema(description = "학습목표 정보")
        List<GoalResponse> goals
) {

    @Builder(access = PRIVATE)
    public record GoalResponse(
            @Schema(description = "학습목표 유형")
            String goalType,
            @Schema(description = "학습목표 이름")
            String name
    ) {
    }
}
