package com.smeme.server.dto.member;

import com.smeme.server.dto.badge.BadgeListResponseDTO;
import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.dto.training.TrainingTimeResponseDTO;
import com.smeme.server.model.Member;

import java.util.List;

public record MemberGetResponseDTO(String username, String target, String targetLang, boolean hasPushAlarm, TrainingTimeResponseDTO trainingTime, List<BadgeResponseDTO> badges) {

    public static MemberGetResponseDTO of(Member member, TrainingTimeResponseDTO trainingTimeDTO, List<BadgeResponseDTO> badgeResponseDTOList) {
        return new MemberGetResponseDTO(
                member.getUsername(),
                member.getGoal().getDescription(),
                member.getTargetLang().toString(),
                member.isHasPushAlarm(),
                trainingTimeDTO,
                badgeResponseDTOList
        );
    }
}
