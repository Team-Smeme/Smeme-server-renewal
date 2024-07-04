package com.smeem.application.port.input.dto.response.member;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.badge.BadgeType;
import com.smeem.application.domain.generic.LangType;
import com.smeem.application.domain.goal.GoalType;
import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.plan.Plan;
import com.smeem.application.domain.trainingtime.TrainingTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder(access = AccessLevel.PRIVATE)
public record RetrieveMemberResponse(
        @Schema(description = "닉네임")
        String username,
        @Schema(description = "학습목표 유형")
        GoalType target,
        @Schema(description = "학습목표 제목")
        String title,
        @Schema(description = "학습목표 방법")
        String way,
        @Schema(description = "학습목표 세부설명")
        String detail,
        @Schema(description = "설정 언어")
        LangType targetLang,
        @Schema(description = "푸시알림 동의 여부")
        boolean hasPushAlarm,
        @Schema(description = "학습시간 정보")
        TrainingTimeResponse trainingTime,
        @Schema(description = "획득한 배지 정보")
        BadgeResponse badge,
        @Schema(description = "학습계획 정보")
        PlanResponse trainingPlan
) {

    public static RetrieveMemberResponse of(Member member, List<TrainingTime> trainingTimes, Badge recentBadge) {
        return RetrieveMemberResponse.builder()
                .username(member.getUsername())
                .target(member.getGoal() != null ? member.getGoal().getGoalType() : null)
                .title(member.getGoal() != null ? member.getGoal().getGoalType().getDescription() : "")
                .way(member.getGoal() != null ? member.getGoal().getWay() : "")
                .detail(member.getGoal() != null ? member.getGoal().getDetail() : "")
                .targetLang(member.getTargetLang())
                .hasPushAlarm(member.isHasPushAlarm())
                .trainingTime(TrainingTimeResponse.of(trainingTimes))
                .badge(recentBadge != null ? BadgeResponse.of(recentBadge) : null)
                .trainingPlan(PlanResponse.of(member.getPlan()))
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private record TrainingTimeResponse(
            @Schema(description = "요일")
            String day,
            @Schema(description = "시간")
            int hour,
            @Schema(description = "분")
            int minute
    ) {

        private static TrainingTimeResponse of(List<TrainingTime> trainingTimes) {
            return TrainingTimeResponse.builder()
                    .day(getDays(trainingTimes))
                    .hour(trainingTimes.isEmpty() ? -1 : trainingTimes.get(0).getHour())
                    .minute(trainingTimes.isEmpty() ? -1 : trainingTimes.get(0).getMinute())
                    .build();
        }

        private static String getDays(List<TrainingTime> trainingTimes) {
            return trainingTimes.stream()
                    .map(trainingTime -> trainingTime.getDayType().name())
                    .distinct()
                    .collect(Collectors.joining(","));
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    private record BadgeResponse(
            @Schema(description = "배지 id")
            Long id,
            @Schema(description = "배지 이름")
            String name,
            @Schema(description = "배지 유형")
            BadgeType type,
            @Schema(description = "배지 이미지 url")
            String imageUrl
    ) {

        private static BadgeResponse of(Badge badge) {
            return BadgeResponse.builder()
                    .id(badge.getId())
                    .name(badge.getName())
                    .type(badge.getBadgeType())
                    .imageUrl(badge.getColorImageUrl())
                    .build();
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    private record PlanResponse(
            @Schema(description = "학습계획 id")
            long id,
            @Schema(description = "학습계획 내용")
            String content
    ) {

        private static PlanResponse of(Plan plan) {
            return PlanResponse.builder()
                    .id(plan.getId())
                    .content(plan.getContent())
                    .build();
        }
    }
}
