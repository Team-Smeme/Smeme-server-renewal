package com.smeme.server.dto.auth;


import com.smeme.server.dto.badge.BadgeResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record SignInResponseDTO(
        @Schema(description = "smeme access token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIyMjU0NjY4LCJleHAiO")
        String accessToken,
        @Schema(description = "smeme refresh token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIyMjU0NjY4LCJleHAiO")
        String refreshToken,

        @Schema(description = "회원 등록 여부", example = "true")
        boolean isRegistered,

        @Schema(description = "학습 계획 설정 여부", example = "false")
        boolean hasPlan,

        List<BadgeResponseDTO> badges
) {

}
