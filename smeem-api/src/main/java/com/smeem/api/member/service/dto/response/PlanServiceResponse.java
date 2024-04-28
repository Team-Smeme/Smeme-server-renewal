package com.smeem.api.member.service.dto.response;


import com.smeem.domain.plan.model.Plan;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record PlanServiceResponse(
        long id,
        String content
) {
    public static PlanServiceResponse of(
            Plan plan
    ) {
        return PlanServiceResponse.builder()
                .id(plan.getId())
                .content(plan.getContent())
                .build();
    }
}
