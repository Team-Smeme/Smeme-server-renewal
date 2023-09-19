package com.smeme.server.dto.member;

import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.dto.training.TrainingTimeResponseDTO;
import com.smeme.server.model.Member;
import com.smeme.server.model.goal.Goal;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberGetResponseDTO(
        @Schema(description = "회원 아이디", example = "test")
        String username,
        @Schema(description = "DEVELOP", example = "영어")
        String target,
        @Schema(description = "목표 달성 방법", example = "주 5회 이상 오늘 하루를 돌아보는 일기 작성하기")
        String way,
        @Schema(description = "목표 세부 내용", example = "사전 없이 일기 완성\nsmeem 연속 일기 배지 획득")
        String detail,
        @Schema(description = "목표 언어", example = "en")
        String targetLang,
        @Schema(description = "푸시 알림 여부", example = "true")
        boolean hasPushAlarm,
        @Schema(description = "학습 시간")
        TrainingTimeResponseDTO trainingTime,
        @Schema(description = "뱃지")
        BadgeResponseDTO badge) {

    public static MemberGetResponseDTO of(Goal goal, Member member, TrainingTimeResponseDTO trainingTime, BadgeResponseDTO badge) {
        return new MemberGetResponseDTO(
                member.getUsername(),
                member.getGoal().getDescription(),
                goal.getWay(),
                goal.getDetail(),
                member.getTargetLang().toString(),
                member.isHasPushAlarm(),
                trainingTime,
                badge
        );
    }
}
