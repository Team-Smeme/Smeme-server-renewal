package com.smeem.api.plan.service.dto.response;

import com.smeem.domain.plan.model.Plan;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record PlansAllGetServiceResponse(
        List<PlanServiceResponse> plans
) {

    public static PlansAllGetServiceResponse of(List<Plan> plans) {
        return PlansAllGetServiceResponse.builder()
                .plans(plans.stream().map(PlanServiceResponse::of).toList())
                .build();
    }

    @Builder(access = PRIVATE)
    public record PlanServiceResponse(
            long id,
            String content
    ) {

        private static PlanServiceResponse of(Plan plan) {
            return PlanServiceResponse.builder()
                    .id(plan.getId())
                    .content(plan.getContent())
                    .build();
        }
    }
}
