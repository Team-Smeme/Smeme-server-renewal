package com.smeem.api.member.service.dto.request;

import com.smeem.api.member.api.dto.request.MemberPlanUpdateRequest;
import com.smeem.domain.goal.model.GoalType;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MemberUpdatePlanServiceRequest(
        GoalType goalType,
        TrainingTimeServiceRequest trainingTime,
        Boolean hasAlarm
) {
    public static MemberUpdatePlanServiceRequest of(MemberPlanUpdateRequest request) {
        return MemberUpdatePlanServiceRequest.builder()
                .goalType(request.target())
                .trainingTime(TrainingTimeServiceRequest.of(request.trainingTime()))
                .hasAlarm(request.hasAlarm())
                .build();
    }
}
