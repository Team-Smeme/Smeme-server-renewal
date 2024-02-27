//package com.smeem.api.fixture.auth;
//
//import com.smeem.api.auth.controller.dto.request.SignInRequest;
//import com.smeem.api.auth.controller.dto.response.SignInResponse;
//import com.smeem.api.auth.controller.dto.response.token.TokenResponse;
//import com.smeem.api.auth.jwt.SmeemToken;
//import com.smeem.domain.member.model.SocialType;
//
//public class AuthFixture {
//
//    private static final SocialType SOCIAL_TYPE = SocialType.KAKAO;
//    private static final String FCM_TOKEN = "testfcmtoken";
//    private static final String ACCESS_TOKEN = "testaccesstoken";
//    private static final String REFRESH_TOKEN = "testrefreshtoken";
//
//    public static SignInRequest createSignInRequestDTO() {
//        return new SignInRequest(
//                SOCIAL_TYPE,
//                FCM_TOKEN
//        );
//    }
//
//    public static SignInResponse createSignInResponseDTO() {
//        return SignInResponse.of(
//                createTokenVO(),
//                true,
//                true
//        );
//    }
//
//    public static SmeemToken createTokenVO() {
//        return SmeemToken.builder()
//                .accessToken(ACCESS_TOKEN)
//                .refreshToken(REFRESH_TOKEN)
//                .build();
//    }
//
//    public static TokenResponse createTokenResponseDTO() {
//        return TokenResponse.of(
//                ACCESS_TOKEN,
//                REFRESH_TOKEN
//        );
//    }
//}
