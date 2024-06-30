package com.smeem.application.port.input.dto.response.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record RetrieveMemberResponse(
        @Schema(description = "닉네임")
        String username,
        @Schema(description = "학습목표 유형")
        String target,
        @Schema(description = "학습목표 제목")
        String title,
        @Schema(description = "학습목표 방법")
        String way,
        @Schema(description = "학습목표 세부설명")
        String detail,
        @Schema(description = "설정 언어")
        String targetLang,
        @Schema(description = "푸시알림 동의 여부")
        boolean hasPushAlarm,
        @Schema(description = "학습시간 정보")
        TrainingTimeResponse trainingTime,
        @Schema(description = "획득한 배지 정보")
        BadgeResponse badge,
        @Schema(description = "학습계획 정보")
        PlanResponse trainingPlan
) {

    @Builder(access = AccessLevel.PRIVATE)
    public record TrainingTimeResponse(
            @Schema(description = "요일")
            String day, //TODO: to ENUM
            @Schema(description = "시간")
            int hour,
            @Schema(description = "분")
            int minute
    ) {
    }

    @Builder(access = AccessLevel.PRIVATE)
    public record BadgeResponse(
            @Schema(description = "배지 id")
            Long id,
            @Schema(description = "배지 이름")
            String name,
            @Schema(description = "배지 유형")
            String type,
            @Schema(description = "배지 이미지 url")
            String imageUrl
    ) {
    }

    @Builder(access = AccessLevel.PRIVATE)
    private record PlanResponse(
            @Schema(description = "학습계획 id")
            long id,
            @Schema(description = "학습계획 내용")
            String content
    ) {
    }
}
