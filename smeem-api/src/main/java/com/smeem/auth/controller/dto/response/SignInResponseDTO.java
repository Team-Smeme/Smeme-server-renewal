package com.smeem.auth.controller.dto.response;


import com.smeem.auth.controller.dto.response.token.TokenVO;
import lombok.Builder;


@Builder
public record SignInResponseDTO(
        String accessToken,
        String refreshToken,
        boolean isRegistered,
        boolean hasPlan
) {

        public static SignInResponseDTO of(TokenVO tokenVO, boolean isRegistered, boolean hasPlan) {
                return new SignInResponseDTO(tokenVO.accessToken(), tokenVO.refreshToken(), isRegistered, hasPlan);
        }

}
