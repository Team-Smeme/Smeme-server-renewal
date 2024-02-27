package com.smeem.api.goal.controller.dto.response;

import com.smeem.api.goal.service.dto.response.GoalGetServiceResponse;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record GoalGetResponse(
        String name,
        String way,
        String detail
) {

    public static GoalGetResponse from(GoalGetServiceResponse response) {
        return GoalGetResponse.builder()
                .name(response.goalType())
                .way(response.way())
                .detail(response.detail())
                .build();
    }
}
