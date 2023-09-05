package com.smeme.server.dto.auth.beta;

import io.swagger.v3.oas.annotations.media.Schema;

public record BetaSignInRequestDTO(
        @Schema(description = "FCM 토큰", example = "dsdsdsdsds")
        String fcmToken
) {
}
