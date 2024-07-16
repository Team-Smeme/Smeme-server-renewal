package com.smeem.application.port.input.dto.response.auth;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.member.Member;
import com.smeem.application.port.input.dto.response.badge.AcquiredBadgeResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record SignInResponse(
        @Schema(description = "스밈 액세스 토큰")
        String accessToken,
        @Schema(description = "스밈 리프레시 토큰")
        String refreshToken,
        @Schema(description = "서비스에 등록된 회원인가?")
        boolean isRegistered,
        @Schema(description = "학습계획을 가진 회원인가?")
        boolean hasPlan,
        @Schema(description = "일기 작성 후 획득한 배지 정보")
        List<AcquiredBadgeResponse> badges
) {

        public static SignInResponse of(String accessToken, Member member, List<Badge> badges) {
                return SignInResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(member.getRefreshToken())
                        .isRegistered(member.getUsername() != null)
                        .hasPlan(member.getGoalId() != null)
                        .badges(badges.stream().map(AcquiredBadgeResponse::from).toList())
                        .build();
        }
}
