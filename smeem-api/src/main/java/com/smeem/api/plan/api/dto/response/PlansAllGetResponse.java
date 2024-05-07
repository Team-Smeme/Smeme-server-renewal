package com.smeem.api.plan.api.dto.response;

import com.smeem.api.plan.service.dto.response.PlansAllGetServiceResponse;
import com.smeem.api.plan.service.dto.response.PlansAllGetServiceResponse.PlanServiceResponse;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record PlansAllGetResponse(
        List<PlanResponse> plans
) {

    public static PlansAllGetResponse from(PlansAllGetServiceResponse response) {
        return PlansAllGetResponse.builder()
                .plans(response.plans().stream().map(PlanResponse::from).toList())
                .build();
    }

    @Builder(access = PRIVATE)
    private record PlanResponse(
            long id,
            String content
    ) {

        private static PlanResponse from(PlanServiceResponse response) {
            return PlanResponse.builder()
                    .id(response.id())
                    .content(response.content())
                    .build();
        }
    }
}
