package com.smeme.server.dto.auth;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.smeme.server.model.SocialType;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignInRequestDTO(
        
        @Schema(description = "소셜 로그인 타입", example = "KAKAO")
        @JsonProperty("social")
        SocialType socialType,

        @Schema(description = "fcm 토큰", example = "dsdsdsdsds")
        String fcmToken
        ) {
}
