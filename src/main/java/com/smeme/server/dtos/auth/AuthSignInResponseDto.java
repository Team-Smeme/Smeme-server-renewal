package com.smeme.server.dtos.auth;

import com.smeme.server.models.User;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record AuthSignInResponseDto(

        @NonNull
        String accessToken,
        @NonNull
        String refreshToken,
        boolean isRegistered
) {
        public static AuthSignInResponseDto from (String accessToken, String refreshToken, boolean isRegistered) {
            return AuthSignInResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .isRegistered(isRegistered)
                    .build();
        }
}
