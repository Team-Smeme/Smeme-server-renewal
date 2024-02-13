package com.smeem.api.goal.controller.dto.response;

import com.smeem.domain.goal.model.GoalType;

import java.util.List;



public record GoalListResponse(
        List<GoalResponseVO> goals
) {
    public static GoalListResponse of(List<GoalType> goalTypes) {
        return new GoalListResponse(goalTypes.stream().map(GoalResponseVO::of).toList());
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
