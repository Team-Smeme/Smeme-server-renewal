package com.smeem.api.goal.controller.dto.response;

import com.smeem.domain.goal.model.GoalType;

import java.util.List;



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
