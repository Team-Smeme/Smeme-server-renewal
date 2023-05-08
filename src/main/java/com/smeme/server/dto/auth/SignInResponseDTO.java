package com.smeme.server.dto.auth;


import lombok.Builder;

@Builder
public record SignInResponseDTO(
        String accessToken,
        String refreshToken,
        boolean isRegistered
) {

}
