package com.smeem.http.controller.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignInRequest(
        @Schema(description = "가입하려는 소셜서비스")
        @JsonProperty("social")
        String socialType,
        @Schema(description = "회원의 FCM 토큰")
        String fcmToken
) {
}
