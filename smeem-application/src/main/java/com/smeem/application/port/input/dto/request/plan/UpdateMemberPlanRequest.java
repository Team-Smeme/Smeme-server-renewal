package com.smeem.application.port.input.dto.request.plan;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateMemberPlanRequest(
        @Schema(description = "학습목표 유형")
        String target, //TODO: GoalType
        @Schema(description = "학습시간 정보")
        TrainingTimeRequest trainingTime,
        @Schema(description = "푸시알림 동의 여부")
        Boolean hasAlarm,
        @Schema(description = "학습계획 id")
        Long planId
) {

    private record TrainingTimeRequest(
            @Schema(description = "요일")
            String day, //TODO: to ENUM
            @Schema(description = "시간")
            Integer hour,
            @Schema(description = "분")
            Integer minute
    ) {
    }
}
