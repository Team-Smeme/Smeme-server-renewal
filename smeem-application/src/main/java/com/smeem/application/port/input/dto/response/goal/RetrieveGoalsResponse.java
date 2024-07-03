package com.smeem.application.port.input.dto.response.goal;

import com.smeem.application.domain.goal.Goal;
import com.smeem.application.domain.goal.GoalType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record RetrieveGoalsResponse(
        @Schema(description = "학습목표 정보")
        List<GoalResponse> goals
) {

    public static RetrieveGoalsResponse of(List<Goal> goals) {
        return RetrieveGoalsResponse.builder()
                .goals(goals.stream().map(GoalResponse::of).toList())
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private record GoalResponse(
            @Schema(description = "학습목표 유형")
            GoalType goalType,
            @Schema(description = "학습목표 이름")
            String name
    ) {

        private static GoalResponse of(Goal goal) {
            return GoalResponse.builder()
                    .goalType(goal.getGoalType())
                    .name(goal.getGoalType().getDescription())
                    .build();
        }
    }
}
