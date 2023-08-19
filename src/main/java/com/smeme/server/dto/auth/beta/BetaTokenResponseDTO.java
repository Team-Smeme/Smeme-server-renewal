package com.smeme.server.dto.auth.beta;

import com.smeme.server.dto.badge.BadgeResponseDTO;

import java.util.List;

public record BetaTokenResponseDTO(
	String accessToken,
	List<BadgeResponseDTO> badges
) {
	public static BetaTokenResponseDTO of(String accessToken, List<BadgeResponseDTO> badges) {
		return new BetaTokenResponseDTO(accessToken, badges);
	}
}
