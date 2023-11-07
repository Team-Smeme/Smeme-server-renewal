package com.smeme.server.dto.goal;

import java.util.List;

import com.smeme.server.model.goal.GoalType;

public record GoalsResponseDTO(
        List<GoalResponseVO> goals
) {
    public static GoalsResponseDTO of(List<GoalType> goalTypes) {
        return new GoalsResponseDTO(goalTypes.stream().map(GoalResponseVO::of).toList());
    }

    public record GoalResponseVO(
            String goalType,
            String name
    ) {
        public static GoalResponseVO of(GoalType goalType) {
            return new GoalResponseVO(goalType.name(), goalType.getDescription());
        }
    }
}
