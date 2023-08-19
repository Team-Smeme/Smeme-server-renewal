package com.smeme.server.dto.auth.token;

import lombok.Builder;

@Builder
public record TokenResponseDTO(
	String accessToken,
	String refreshToken
) {
}
