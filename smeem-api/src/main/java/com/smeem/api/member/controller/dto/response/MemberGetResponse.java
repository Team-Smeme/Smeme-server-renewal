package com.smeem.api.member.controller.dto.response;


import com.smeem.api.badge.controller.dto.response.BadgeResponse;
import com.smeem.api.member.service.dto.response.MemberGetServiceResponse;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MemberGetResponse(
        String username,
        String target,
        String way,
        String detail,
        String targetLang,
        boolean hasPushAlarm,
        TrainingTimeResponse trainingTime,
        BadgeResponse badge
) {

    public static MemberGetResponse of(MemberGetServiceResponse response) {
        return MemberGetResponse.builder()
                .username(response.username())
                .target(response.target())
                .way(response.way())
                .detail(response.detail())
                .targetLang(response.targetLangType().toString())
                .hasPushAlarm(response.hasPushAlarm())
                .trainingTime(TrainingTimeResponse.of(response.trainingTime()))
                .badge(BadgeResponse.of(response.badge()))
                .build();
    }
}
