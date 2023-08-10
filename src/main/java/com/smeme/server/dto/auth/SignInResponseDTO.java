package com.smeme.server.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;


@Builder
public record SignInResponseDTO(
        @Schema(description = "smeme access token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIyMjU0NjY4LCJleHAiO")
        String accessToken,
        @Schema(description = "smeme refresh token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIyMjU0NjY4LCJleHAiO")
        String refreshToken,

        @Schema(description = "회원 정보 등록 여부", example = "true")
        boolean isRegistered
) {

}
