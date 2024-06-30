package com.smeem.http.controller.dto.response.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignInResponse(
        @Schema(description = "스밈 액세스 토큰")
        String accessToken,
        @Schema(description = "스밈 리프레시 토큰")
        String refreshToken,
        @Schema(description = "서비스에 등록된 회원인가?")
        boolean isRegistered,
        @Schema(description = "학습계획을 가진 회원인가?")
        boolean hasPlan
) {
}
