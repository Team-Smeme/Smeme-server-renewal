package com.smeem.application.port.input.dto.response.auth;

import com.smeem.application.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
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

        public static SignInResponse of(String accessToken, Member member) {
                return SignInResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(member.getRefreshToken())
                        .isRegistered(member.getUsername() != null)
                        .hasPlan(member.getGoal() != null)
                        .build();
        }
}
