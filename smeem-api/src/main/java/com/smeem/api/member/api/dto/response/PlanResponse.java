package com.smeem.api.member.api.dto.response;

import com.smeem.api.member.service.dto.response.PlanServiceResponse;

public record PlanResponse(
        long id,
        String content
) {
    public static PlanResponse from(PlanServiceResponse response) {
        return new PlanResponse(
                response.id(),
                response.content()
        );
    }
}
