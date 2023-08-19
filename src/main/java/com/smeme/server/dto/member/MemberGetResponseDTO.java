package com.smeme.server.dto.member;

import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.dto.training.TrainingTimeResponseDTO;
import com.smeme.server.model.Member;
import com.smeme.server.model.goal.Goal;

import lombok.Builder;

@Builder
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
	public static MemberGetResponseDTO of(
		Goal goal, Member member, TrainingTimeResponseDTO trainingTime, BadgeResponseDTO badge
	) {
		return MemberGetResponseDTO.builder()
			.username(member.getUsername())
			.target(member.getGoal().getDescription())
			.way(goal.getWay())
			.detail(goal.getDetail())
			.targetLang(member.getTargetLang().toString())
			.hasPushAlarm(member.isHasPushAlarm())
			.trainingTime(trainingTime)
			.badge(badge)
			.build();
	}
}
