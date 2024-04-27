package com.smeem.api.member.api.dto.response;


import com.smeem.api.badge.api.dto.response.BadgeResponse;
import com.smeem.api.member.service.dto.response.MemberGetServiceResponse;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MemberGetResponse(
        String username,
        String target,
        String title,
        String way,
        String detail,
        String targetLang,
        boolean hasPushAlarm,
        TrainingTimeResponse trainingTime,
        BadgeResponse badge,
        Long trainingPlanId,
        String trainingPlanContent
) {

    public static MemberGetResponse from(MemberGetServiceResponse response) {
        return MemberGetResponse.builder()
                .username(response.username())
                .target(response.goalType().name())
                .title(response.goalType().getDescription())
                .way(response.way())
                .detail(response.detail())
                .targetLang(response.targetLangType().toString())
                .hasPushAlarm(response.hasPushAlarm())
                .trainingTime(TrainingTimeResponse.from(response.trainingTime()))
                .badge(BadgeResponse.from(response.badge()))
                .trainingPlanId(response.trainingPlanId())
                .trainingPlanContent(response.trainingPlanContent())
                .build();
    }
}
