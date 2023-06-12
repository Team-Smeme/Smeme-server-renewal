package com.smeme.server.dto.member;

import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.dto.training.TrainingTimeResponseDTO;
import com.smeme.server.model.Member;
import com.smeme.server.model.goal.Goal;

import java.util.List;

public record MemberGetResponseDTO(String username, String target, String way, String detail, String targetLang, boolean hasPushAlarm, TrainingTimeResponseDTO trainingTime, List<BadgeResponseDTO> badges) {

    public static MemberGetResponseDTO of(Goal goal,Member member, TrainingTimeResponseDTO trainingTime, List<BadgeResponseDTO> badges) {
        return new MemberGetResponseDTO(
                member.getUsername(),
                member.getGoal().getDescription(),
                goal.getWay(),
                goal.getDetail(),
                member.getTargetLang().toString(),
                member.isHasPushAlarm(),
                trainingTime,
                badges
        );
    }
}
