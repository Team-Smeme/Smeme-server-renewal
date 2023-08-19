package com.smeme.server.dto.member;

import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.dto.training.TrainingTimeResponseDTO;
import com.smeme.server.model.Member;
import com.smeme.server.model.goal.Goal;

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
