package com.smeem.http.controller.dto.response.plan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record RetrievePlansResponse(
        @Schema(description = "학습계획 정보")
        List<PlanResponse> plans
) {

    @Builder(access = PRIVATE)
    private record PlanResponse(
            @Schema(description = "학습계획 id")
            long id,
            @Schema(description = "학습계획 내용")
            String content
    ) {
    }
}
