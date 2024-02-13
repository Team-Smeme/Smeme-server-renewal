package com.smeem.api.goal.controller.dto.response;


import com.smeem.domain.goal.model.Goal;

public record GoalResponse(
        String name,
        String way,
        String detail
) {

    public static GoalResponse of(Goal goal) {
        return new GoalResponse(
                goal.getType().name(),
                goal.getWay(),
                goal.getDetail()
        );
    }
}
