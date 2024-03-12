package com.smeem.api.goal.api.dto.response;

import com.smeem.api.goal.service.dto.response.GoalGetServiceResponse;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record GoalGetResponse(
        String name,
        String title,
        String way,
        String detail
) {

    public static GoalGetResponse from(GoalGetServiceResponse response) {
        return GoalGetResponse.builder()
                .name(response.goalType().name())
                .title(response.goalType().getDescription())
                .way(response.way())
                .detail(response.detail())
                .build();
    }
}
