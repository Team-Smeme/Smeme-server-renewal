package com.smeme.server.dto.goal;

import java.util.List;

import com.smeme.server.model.goal.GoalType;

public record GoalsResponseDTO(
	List<GoalResponseDTO> goals
) {
	public static GoalsResponseDTO of(List<GoalType> goalTypes) {
		return new GoalsResponseDTO(goalTypes.stream().map(GoalResponseDTO::of).toList());
	}

	public record GoalResponseDTO(
		GoalType goalType,
		String name
	) {
		public static GoalResponseDTO of(GoalType goalType) {
			return new GoalResponseDTO(goalType, goalType.getDescription());
		}
	}
}
