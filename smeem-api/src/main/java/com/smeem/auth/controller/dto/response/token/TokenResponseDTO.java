package com.smeem.auth.controller.dto.response.token;

import lombok.Builder;

@Builder
public record TokenResponseDTO(
        String accessToken,
        String refreshToken
) {

    public static TokenResponseDTO of(String accessToken, String refreshToken) {
        return new TokenResponseDTO(accessToken, refreshToken);
    }
}
