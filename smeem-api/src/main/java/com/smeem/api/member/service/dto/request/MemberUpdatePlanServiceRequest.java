package com.smeem.api.member.service.dto.request;

import com.smeem.api.member.api.dto.request.MemberPlanUpdateRequest;
import com.smeem.domain.goal.model.GoalType;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MemberUpdatePlanServiceRequest(
        long memberId,
        GoalType goalType,
        TrainingTimeServiceRequest trainingTime,
        Boolean hasAlarm,
        Long planId
) {

    public static MemberUpdatePlanServiceRequest of(long memberId, MemberPlanUpdateRequest request) {
        return MemberUpdatePlanServiceRequest.builder()
                .memberId(memberId)
                .goalType(request.target())
                .trainingTime(TrainingTimeServiceRequest.of(request.trainingTime()))
                .hasAlarm(request.hasAlarm())
                .planId(request.planId())
                .build();
    }
}
