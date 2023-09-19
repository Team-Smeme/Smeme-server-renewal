package com.smeme.server.dto.goal;

import java.util.List;

import com.smeme.server.model.goal.GoalType;

import io.swagger.v3.oas.annotations.media.Schema;

public record GoalsResponseDTO(
        @Schema(description = "학습 목표 리스트")
        List<GoalResponseVO> goals
) {
    public static GoalsResponseDTO of(List<GoalType> goalTypes) {
        return new GoalsResponseDTO(goalTypes.stream().map(GoalResponseVO::of).toList());
    }

    record GoalResponseVO(
            @Schema(description = "학습 목표 ENUM 값", example = "APPLY")
            String goalType,
            @Schema(description = "학습 목표 이름", example = "현지 언어 체득")
            String name
    ) {
        public static GoalResponseVO of(GoalType goalType) {
            return new GoalResponseVO(goalType.name(), goalType.getDescription());
        }
    }
}
