package com.smeme.server.dto.goal;

import com.smeme.server.model.goal.Goal;

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
