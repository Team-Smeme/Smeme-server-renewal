package com.smeem.api.goal.controller.dto.response;

import com.smeem.api.goal.service.dto.response.GoalListGetServiceResponse;
import com.smeem.api.goal.service.dto.response.GoalListGetServiceResponse.GoalServiceResponse;
import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record GoalListGetResponse(
        List<GoalResponse> goals
) {

    public static GoalListGetResponse from(GoalListGetServiceResponse response) {
        return GoalListGetResponse.builder()
                .goals(response.goals().stream().map(GoalResponse::from).toList())
                .build();
    }

    @Builder(access = PRIVATE)
    public record GoalResponse(
            String goalType,
            String name
    ) {

        private static GoalResponse from(GoalServiceResponse response) {
            return GoalResponse.builder()
                    .goalType(response.goalType())
                    .name(response.goalDescription())
                    .build();
        }
    }
}
