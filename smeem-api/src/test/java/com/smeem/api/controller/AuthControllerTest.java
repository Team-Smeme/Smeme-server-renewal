package com.smeem.api.controller;


import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.smeem.api.fixture.auth.AuthFixture;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.smeme.server.util.ApiResponse.success;
import static com.smeme.server.util.message.ResponseMessage.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AuthController 테스트")
@WebMvcTest(AuthController.class)
public class AuthControllerTest extends BaseControllerTest {

    @MockBean
    AuthController authController;

    @MockBean
    Principal principal;

    private final String DEFAULT_URL = "/api/v2/com.smeem.auth";
    private final String TAG = "Auth";

    private static final String SOCIAL_ACCESS_TOKEN = "TEST SOCIAL_ACCESS_TOKEN";

    @DisplayName("소셜로그인 API 성공")
    @Test
    void success_signIn() throws Exception {

        val resources = ResourceSnippetParameters.builder()
                .tag(TAG)
                .description("소셜로그인")
                .requestHeaders(
                        headerWithName("Authorization").description("소셜 로그인 토큰")
                )
                .requestFields(
                        fieldWithPath("social").type(STRING).description("소셜로그인 타입"),
                        fieldWithPath("fcmToken").type(STRING).description("FCM Token")
                )
                .responseFields(
                        fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                        fieldWithPath("data.accessToken").type(STRING).description("서비스 액세스 토큰"),
                        fieldWithPath("data.refreshToken").type(STRING).description("서비스 리프레시 토큰"),
                        fieldWithPath("data.isRegistered").type(BOOLEAN).description("서비스 등록 여부"),
                        fieldWithPath("data.hasPlan").type(BOOLEAN).description("학습 계획 설정 여부")
                )
                .build();
        // when
        val result =  ResponseEntity.ok(success(SUCCESS_SIGNIN.getMessage(), AuthFixture.createSignInResponseDTO()));

        when(authController.signIn(SOCIAL_ACCESS_TOKEN , AuthFixture.createSignInRequestDTO()))
                .thenReturn(result);


        mockMvc.perform(post(DEFAULT_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .principal(principal)
                        .header("Authorization", SOCIAL_ACCESS_TOKEN)
                        .content(objectMapper.writeValueAsString(AuthFixture.createSignInRequestDTO())))
                .andDo(
                        document("소셜로그인 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(resources)
                        ))
                .andExpect(status().isOk());
    }

    @DisplayName("토큰재발급 API 성공")
    @Test
    void success_reissueToken() throws Exception {
        val resources = ResourceSnippetParameters.builder()
                .tag(TAG)
                .description("토큰재발급 API")
                .responseFields(
                        fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                        fieldWithPath("data.accessToken").type(STRING).description("서비스 액세스 토큰"),
                        fieldWithPath("data.refreshToken").type(STRING).description("서비스 리프레시 토큰")
                )
                .build();
        // when
        val result = ResponseEntity.ok(success(SUCCESS_ISSUE_TOKEN.getMessage(), AuthFixture.createTokenResponseDTO()));

        when(authController.reissueToken(principal)).thenReturn(result);

        mockMvc.perform(post(DEFAULT_URL + "/token")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .principal(principal))
                .andDo(
                        document("토큰재발급 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(resources)
                        ))
                .andExpect(status().isOk());
    }

    @DisplayName("로그아웃 API 성공")
    @Test
    void success_signOut() throws Exception {
        val resources = ResourceSnippetParameters.builder()
                .tag(TAG)
                .description("로그아웃 API")
                .responseFields(
                        fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                        fieldWithPath("data").type(NULL).description("응답 데이터")
                )
                .build();
        // when
        val result = ResponseEntity.ok(success(SUCCESS_SIGNOUT.getMessage()));

        when(authController.signOut(principal)).thenReturn(result);

        mockMvc.perform(post(DEFAULT_URL + "/sign-out")
                        .principal(principal))
                .andDo(
                        document("로그아웃 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(resources)
                        ))
                .andExpect(status().isOk());
    }

    @DisplayName("회원탈퇴 API 성공")
    @Test
    void success_withDrawl() throws Exception{
        val resources = ResourceSnippetParameters.builder()
                .tag(TAG)
                .description("회원탈퇴 API")
                .responseFields(
                        fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                        fieldWithPath("data").type(NULL).description("응답 데이터")
                )
                .build();
        // when
        val result = ResponseEntity.ok(success(SUCCESS_WITHDRAW.getMessage()));

        when(authController.withDrawl(principal)).thenReturn(result);

        mockMvc.perform(delete(DEFAULT_URL)
                        .principal(principal))
                .andDo(
                        document("회원탈퇴 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(resources)
                        ))
                .andExpect(status().isOk());
    }
}
