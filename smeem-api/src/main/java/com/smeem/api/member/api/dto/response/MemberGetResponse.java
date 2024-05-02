package com.smeem.api.member.api.dto.response;

import com.smeem.api.badge.api.dto.response.BadgeResponse;
import com.smeem.api.member.service.dto.response.MemberGetServiceResponse;
import com.smeem.api.member.service.dto.response.MemberGetServiceResponse.PlanServiceResponse;
import lombok.Builder;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
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
        PlanResponse trainingPlan
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
                .trainingPlan(PlanResponse.from(response.trainingPlan()))
                .build();
    }

    @Builder(access = PRIVATE)
    private record PlanResponse(
            long id,
            String content
    ) {

        private static PlanResponse from(PlanServiceResponse response) {
            return nonNull(response)
                    ? PlanResponse.builder()
                        .id(response.id())
                        .content(response.content())
                        .build()
                    : null;
        }
    }
}
