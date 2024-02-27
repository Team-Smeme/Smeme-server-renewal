package com.smeem.api.member.controller.dto.request;


import com.smeem.domain.goal.model.GoalType;

public record MemberPlanUpdateRequest(
        GoalType target,
        TrainingTimeRequest trainingTime,
        Boolean hasAlarm
) {
}
