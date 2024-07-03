package com.smeem.application.port.input.dto.response.goal;

import com.smeem.application.domain.goal.Goal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record RetrieveGoalResponse(
        @Schema(description = "학습목표 이름")
        String name,
        @Schema(description = "학습목표 제목")
        String title,
        @Schema(description = "학습목표 방법")
        String way,
        @Schema(description = "학습목표 세부내용")
        String detail
) {

        public static RetrieveGoalResponse of(Goal goal) {
                return RetrieveGoalResponse.builder()
                        .name(goal.getGoalType().name())
                        .title(goal.getGoalType().getDescription())
                        .way(goal.getWay())
                        .detail(goal.getDetail())
                        .build();
        }
}
