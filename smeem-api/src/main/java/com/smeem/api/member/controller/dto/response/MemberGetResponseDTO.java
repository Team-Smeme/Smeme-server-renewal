package com.smeem.api.member.controller.dto.response;


import com.smeem.api.badge.controller.dto.response.BadgeResponseDTO;
import com.smeem.api.goal.controller.dto.response.GoalResponseDTO;
import com.smeem.domain.member.model.Member;

public record MemberGetResponseDTO(
        String username,
        String target,
        String way,
        String detail,
        String targetLang,
        boolean hasPushAlarm,
        TrainingTimeResponseDTO trainingTime,
        BadgeResponseDTO badge
) {

    public static MemberGetResponseDTO of(GoalResponseDTO goal, Member member, TrainingTimeResponseDTO trainingTime, BadgeResponseDTO badge) {
        return new MemberGetResponseDTO(
                member.getUsername(),
                member.getGoal().getDescription(),
                goal.way(),
                goal.detail(),
                member.getTargetLang().toString(),
                member.isHasPushAlarm(),
                trainingTime,
                badge
        );
    }
}
