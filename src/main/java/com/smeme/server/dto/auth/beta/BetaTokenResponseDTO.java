package com.smeme.server.dto.auth.beta;

import com.smeme.server.dto.badge.BadgeResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record BetaTokenResponseDTO(
        @Schema(description = "smeme beta access token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIyMjU0NjY4LCJleHAiO")
        String accessToken,
        List<BadgeResponseDTO> badges
) {
    public static BetaTokenResponseDTO of(String accessToken, List<BadgeResponseDTO> badges) {
        return new BetaTokenResponseDTO(accessToken, badges);
    }
}
