package com.smeme.server.dto.member;

import com.smeme.server.dto.training.TrainingTimeRequestDTO;
import com.smeme.server.model.goal.GoalType;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "사용자 학습 계획 수정 요청")
public record MemberPlanUpdateRequestDTO(
        @Schema(description = "목표 타입", example = "HOBBY")
        GoalType target,
        @Schema(description = "학습 시간")
        TrainingTimeRequestDTO trainingTime,
        @Schema(description = "알람 여부", example = "true")
        Boolean hasAlarm
) {
}
