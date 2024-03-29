package com.smeem.api.member.service.dto.response;

import com.smeem.api.badge.service.dto.response.BadgeServiceResponse;
import com.smeem.api.goal.service.dto.response.GoalGetServiceResponse;
import com.smeem.domain.goal.model.GoalType;
import com.smeem.domain.member.model.LangType;
import com.smeem.domain.member.model.Member;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;


@Builder(access = PRIVATE)
public record MemberGetServiceResponse(
        String username,
        GoalType goalType,
        String way,
        String detail,
        LangType targetLangType,
        boolean hasPushAlarm,
        TrainingTimeServiceResponse trainingTime,
        BadgeServiceResponse badge
) {

    public static MemberGetServiceResponse of(
            GoalGetServiceResponse goal,
            Member member,
            TrainingTimeServiceResponse trainingTime,
            BadgeServiceResponse badge
    )  {
        return MemberGetServiceResponse.builder()
                .username(member.getUsername())
                .goalType(goal.goalType())
                .way(goal.way())
                .detail(goal.detail())
                .targetLangType(member.getTargetLang())
                .hasPushAlarm(member.isHasPushAlarm())
                .trainingTime(trainingTime)
                .badge(badge)
                .build();
    }
}
