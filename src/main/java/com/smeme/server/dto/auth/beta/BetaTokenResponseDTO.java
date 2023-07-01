package com.smeme.server.dto.auth.beta;

import com.smeme.server.dto.badge.BadgeResponseDTO;

public record BetaTokenResponseDTO(
        String accessToken,
        BadgeResponseDTO badge
) {
    public static BetaTokenResponseDTO of(String accessToken, BadgeResponseDTO badge) {
        return new BetaTokenResponseDTO(accessToken, badge);
    }
}
