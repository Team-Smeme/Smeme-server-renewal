package com.smeme.server.dtos.auth;

import lombok.Builder;

@Builder
public record AuthGetTokenResponseDto(
        String accessToken,
        String refreshToken
) {
}
