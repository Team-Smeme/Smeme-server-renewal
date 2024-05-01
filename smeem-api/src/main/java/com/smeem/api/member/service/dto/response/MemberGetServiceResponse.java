package com.smeem.api.member.service.dto.response;

import com.smeem.api.badge.service.dto.response.BadgeServiceResponse;
import com.smeem.api.goal.service.dto.response.GoalGetServiceResponse;
import com.smeem.domain.goal.model.GoalType;
import com.smeem.domain.member.model.LangType;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.plan.model.Plan;
import com.smeem.domain.training.exception.TrainingTimeException;
import com.smeem.domain.training.model.TrainingTime;
import lombok.Builder;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

import static com.smeem.common.code.failure.TrainingTimeFailureCode.NOT_SET_TRAINING_TIME;
import static java.util.Objects.nonNull;
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
        BadgeServiceResponse badge,
        PlanServiceResponse trainingPlan
) {

    public static MemberGetServiceResponse of(GoalGetServiceResponse goal, Member member, BadgeServiceResponse badge) {
        return MemberGetServiceResponse.builder()
                .username(member.getUsername())
                .goalType(member.getGoal())
                .way(goal.way())
                .detail(goal.detail())
                .targetLangType(member.getTargetLang())
                .hasPushAlarm(member.isHasPushAlarm())
                .trainingTime(generateTrainingTimeResponse(member.getTrainingTimes()))
                .badge(badge)
                .trainingPlan(PlanServiceResponse.of(member))
                .build();
    }

    private static String getDays(List<TrainingTime> trainingTimes) {
        return trainingTimes.stream()
                .map(trainingTime -> trainingTime.getDay().name())
                .distinct()
                .collect(Collectors.joining(","));
    }

    private static TrainingTimeServiceResponse generateTrainingTimeResponse(List<TrainingTime> trainingTimes) {
        if (trainingTimes.isEmpty()) {
            return TrainingTimeServiceResponse.of("", 22, 0);
        }
        val trainingTime = getOneTrainingTime(trainingTimes);
        return TrainingTimeServiceResponse.of(
                getDays(trainingTimes),
                trainingTime.getHour(),
                trainingTime.getMinute());
    }

    private static TrainingTime getOneTrainingTime(List<TrainingTime> trainingTimes) {
        return trainingTimes.stream().findFirst().orElseThrow(
                () -> new TrainingTimeException(NOT_SET_TRAINING_TIME));
    }

    @Builder(access = PRIVATE)
    public record PlanServiceResponse(
            long id,
            String content
    ) {

        private static PlanServiceResponse of(Member member) {
            return nonNull(member.getPlan()) ? PlanServiceResponse.of(member.getPlan()) : null;
        }

        private static PlanServiceResponse of(Plan plan) {
            return PlanServiceResponse.builder()
                    .id(plan.getId())
                    .content(plan.getContent())
                    .build();
        }
    }

}
