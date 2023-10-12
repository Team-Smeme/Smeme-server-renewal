package com.smeme.server.dto.auth;


import com.smeme.server.dto.auth.token.TokenVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;


@Builder
public record SignInResponseDTO(
        @Schema(description = "smeme access token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIyMjU0NjY4LCJleHAiO")
        String accessToken,
        @Schema(description = "smeme refresh token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIyMjU0NjY4LCJleHAiO")
        String refreshToken,
        @Schema(description = "회원 정보 등록 여부", example = "true")
        boolean isRegistered,
        @Schema(description = "회원의 목표 언어", example = "EN")
        boolean hasPlan
) {

        public static SignInResponseDTO of(TokenVO tokenVO, boolean isRegistered, boolean hasPlan) {
                return new SignInResponseDTO(tokenVO.accessToken(), tokenVO.refreshToken(), isRegistered, hasPlan);
        }

}
