package com.smeem.application.port.input.dto.request.plan;

import com.smeem.application.domain.goal.GoalType;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateMemberPlanRequest(
        @Schema(description = "수정할 학습목표 유형")
        GoalType target,
        @Schema(description = "수정할 학습시간 정보")
        TrainingTimeRequest trainingTime,
        @Schema(description = "수정할 푸시알림 동의 여부")
        Boolean hasAlarm,
        @Schema(description = "수정할 학습계획 id")
        Long planId
) {

    public record TrainingTimeRequest(
            @Schema(description = "요일")
            String day,
            @Schema(description = "시간")
            Integer hour,
            @Schema(description = "분")
            Integer minute
    ) {
    }
}
