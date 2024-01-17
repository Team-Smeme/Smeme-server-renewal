package com.smeme.server.dto.member;

import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.dto.goal.GoalResponseDTO;
import com.smeme.server.dto.training.TrainingTimeResponseDTO;
import com.smeme.server.model.Member;

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
