package com.smeem.application.port.input.dto.response.plan;

import com.smeem.application.domain.goal.Goal;
import com.smeem.application.domain.plan.Plan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record RetrieveMemberPlanResponse(
        @Schema(description = "학습계획")
        String plan,
        @Schema(description = "학습목표")
        String goal,
        @Schema(description = "성취한 횟수")
        int clearedCount,
        @Schema(description = "전체 성취 횟수")
        Integer clearCount
) {

        public static RetrieveMemberPlanResponse of(Goal goal, Plan plan, int diaryCount) {
                return RetrieveMemberPlanResponse.builder()
                        .plan(plan != null ? plan.getContent() : null)
                        .goal(goal.getGoalType().getDescription())
                        .clearCount(plan != null ? plan.getClearCount() : null)
                        .clearedCount(diaryCount)
                        .build();
        }
}
