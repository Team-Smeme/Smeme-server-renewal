package com.smeem.api.fixture.auth;

import com.smeem.api.auth.controller.dto.request.SignInRequestDTO;
import com.smeem.api.auth.controller.dto.response.SignInResponseDTO;
import com.smeem.api.auth.controller.dto.response.token.TokenResponseDTO;
import com.smeem.api.auth.controller.dto.response.token.TokenVO;
import com.smeem.domain.member.model.SocialType;

public class AuthFixture {

    private static final SocialType SOCIAL_TYPE = SocialType.KAKAO;
    private static final String FCM_TOKEN = "testfcmtoken";
    private static final String ACCESS_TOKEN = "testaccesstoken";
    private static final String REFRESH_TOKEN = "testrefreshtoken";

    public static SignInRequestDTO createSignInRequestDTO() {
        return new SignInRequestDTO(
                SOCIAL_TYPE,
                FCM_TOKEN
        );
    }

    public static SignInResponseDTO createSignInResponseDTO() {
        return SignInResponseDTO.of(
                createTokenVO(),
                true,
                true
        );
    }

    public static TokenVO createTokenVO() {
        return new TokenVO(
                ACCESS_TOKEN,
                REFRESH_TOKEN
        );
    }

    public static TokenResponseDTO createTokenResponseDTO() {
        return TokenResponseDTO.of(
                ACCESS_TOKEN,
                REFRESH_TOKEN
        );
    }
}
