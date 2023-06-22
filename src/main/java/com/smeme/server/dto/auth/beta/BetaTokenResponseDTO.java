package com.smeme.server.dto.auth.beta;

public record BetaTokenResponseDTO(
        String accessToken
) {
    public static BetaTokenResponseDTO of(String accessToken) {
        return new BetaTokenResponseDTO(accessToken);
    }
}
