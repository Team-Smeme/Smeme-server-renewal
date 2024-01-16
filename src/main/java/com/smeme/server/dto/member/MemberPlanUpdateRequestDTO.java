package com.smeme.server.dto.member;

import com.smeme.server.dto.training.TrainingTimeRequestDTO;
import com.smeme.server.model.goal.GoalType;

public record MemberPlanUpdateRequestDTO(
        GoalType target,
        TrainingTimeRequestDTO trainingTime,
        Boolean hasAlarm
) {
}
