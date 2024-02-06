package com.smeem.fixture.auth;

import com.smeme.server.dto.auth.SignInRequestDTO;
import com.smeme.server.dto.auth.SignInResponseDTO;
import com.smeem.auth.controller.dto.response.token.TokenResponseDTO;
import com.smeem.auth.controller.dto.response.token.TokenVO;
import com.smeme.server.model.SocialType;

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
