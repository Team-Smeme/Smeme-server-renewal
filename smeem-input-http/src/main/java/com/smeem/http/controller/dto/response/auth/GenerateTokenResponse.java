package com.smeem.http.controller.dto.response.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GenerateTokenResponse(
        @Schema(description = "스밈 액세스 토큰")
        String accessToken,
        @Schema(description = "스밈 리프레시 토큰")
        String refreshToken
) {
}
