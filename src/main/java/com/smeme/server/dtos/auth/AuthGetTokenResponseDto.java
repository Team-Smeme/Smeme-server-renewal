package com.smeme.server.dtos.auth;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record AuthGetTokenResponseDto(
        @NonNull
        String accessToken,
        @NonNull
        String refreshToken
) {
}
