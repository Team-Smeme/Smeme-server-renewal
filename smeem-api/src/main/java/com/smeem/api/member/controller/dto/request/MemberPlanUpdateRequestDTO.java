package com.smeem.api.member.controller.dto.request;


import com.smeem.domain.goal.model.GoalType;

public record MemberPlanUpdateRequestDTO(
        GoalType target,
        TrainingTimeRequestDTO trainingTime,
        Boolean hasAlarm
) {
}
