package com.smeem.application.port.input.dto.response.plan;

import com.smeem.application.domain.plan.Plan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record RetrievePlansResponse(
        @Schema(description = "학습계획 정보")
        List<PlanResponse> plans
) {

    public static RetrievePlansResponse of(List<Plan> plans) {
        return RetrievePlansResponse.builder()
                .plans(plans.stream().map(PlanResponse::of).toList())
                .build();
    }

    @Builder(access = PRIVATE)
    private record PlanResponse(
            @Schema(description = "학습계획 id")
            long id,
            @Schema(description = "학습계획 내용")
            String content
    ) {

        private static PlanResponse of(Plan plan) {
            return PlanResponse.builder()
                    .id(plan.getId())
                    .content(plan.getContent())
                    .build();
        }
    }
}
