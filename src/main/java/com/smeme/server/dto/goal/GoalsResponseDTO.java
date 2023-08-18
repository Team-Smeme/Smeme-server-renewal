package com.smeme.server.dto.goal;

import java.util.List;

import com.smeme.server.model.goal.GoalType;

public record GoalsResponseDTO(
	List<GoalResponseVO> goals
) {
	public static GoalsResponseDTO of(List<GoalType> goalTypes) {
		return new GoalsResponseDTO(goalTypes.stream().map(GoalResponseVO::of).toList());
	}

	public static GoalsResponseDTO testOf() {
		return new GoalsResponseDTO(List.of(GoalResponseVO.testOf()));
	}
}

record GoalResponseVO(
	GoalType goalType,
	String name
) {
	public static GoalResponseVO of(GoalType goalType) {
		return new GoalResponseVO(goalType, goalType.getDescription());
	}

	public static GoalResponseVO testOf() {
		return new GoalResponseVO(GoalType.APPLY, "현지 언어 체득");
	}
}
