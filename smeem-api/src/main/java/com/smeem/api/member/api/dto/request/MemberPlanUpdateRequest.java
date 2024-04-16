package com.smeem.api.member.api.dto.request;


import com.smeem.domain.goal.model.GoalType;

public record MemberPlanUpdateRequest(
        GoalType target,
        TrainingTimeRequest trainingTime,
        Boolean hasAlarm,
        Long planId
) {
}
