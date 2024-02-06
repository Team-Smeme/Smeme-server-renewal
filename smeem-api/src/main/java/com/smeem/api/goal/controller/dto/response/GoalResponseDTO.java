package com.smeem.api.goal.controller.dto.response;


import com.smeem.domain.goal.model.Goal;

public record GoalResponseDTO(
        String name,
        String way,
        String detail
) {

    public static GoalResponseDTO of(Goal goal) {
        return new GoalResponseDTO(
                goal.getType().name(),
                goal.getWay(),
                goal.getDetail()
        );
    }
}
