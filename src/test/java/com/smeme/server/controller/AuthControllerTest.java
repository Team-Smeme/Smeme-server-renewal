package com.smeme.server.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static com.smeme.server.controller.Util.*;
import static com.smeme.server.util.ApiResponse.*;
import static com.smeme.server.util.message.ResponseMessage.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.ResultActions;

import com.epages.restdocs.apispec.HeaderDescriptorWithType;
import com.smeme.server.dto.auth.SignInRequestDTO;
import com.smeme.server.dto.auth.SignInResponseDTO;
import com.smeme.server.dto.auth.token.TokenResponseDTO;
import com.smeme.server.model.SocialType;
import com.smeme.server.util.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@DisplayName("AuthController 테스트")
@WebMvcTest(AuthController.class)
class AuthControllerTest extends BaseControllerTest {

	private static final String DEFAULT_URL = "/api/v2/auth";
	private final String TAG = "Auth";
	private final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIyMjU0NjY4LCJleHAiO";

	@MockBean
	AuthController authController;
	@MockBean
	Principal principal;

	@Test
	@DisplayName("소셜 로그인 테스트")
	void success_sign_in_test() throws Exception {
		// given
		String otherServiceToken = "dsdsdsdsds";
		SignInRequestDTO requestDTO = new SignInRequestDTO(SocialType.KAKAO, otherServiceToken);
		SignInResponseDTO responseDTO = new SignInResponseDTO(TOKEN, TOKEN, true, true);
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(SUCCESS_SIGNIN.getMessage(), responseDTO));

		// when
		when(authController.signIn(otherServiceToken, requestDTO)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc.perform(
			post(DEFAULT_URL)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.header("Authorization", otherServiceToken)
				.content(objectMapper.writeValueAsString(requestDTO)));

		List<HeaderDescriptorWithType> requestHeaders = new ArrayList<>();
		requestHeaders.add(headerWithName("Authorization").description("소셜 서비스 액세스 토큰"));

		List<FieldDescriptor> requestFields = new ArrayList<>();
		requestFields.add(fieldWithPath("social").type(STRING).description("소셜 로그인 타입"));
		requestFields.add(fieldWithPath("fcmToken").type(STRING).description("fcm 토큰"));

		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(OBJECT).description("응답 데이터"));
		responseFields.add(fieldWithPath("data.accessToken").type(STRING).description("smeem access token"));
		responseFields.add(fieldWithPath("data.refreshToken").type(STRING).description("smeem refresh token"));
		responseFields.add(fieldWithPath("data.isRegistered").type(BOOLEAN).description("회원 정보 등록 여부"));
		responseFields.add(fieldWithPath("data.hasPlan").type(BOOLEAN).description("회원의 목표 유무"));

		doDocument(resultActions,
			"Sign In Test",
			TAG,
			"소셜 로그인",
			requestHeaders,
			requestFields,
			new ArrayList<>(),
			responseFields,
			status().isOk());
	}

	@Test
	@DisplayName("토큰 재발급 테스트")
	void success_reissue_token_test() throws Exception {
		// given
		String message = SUCCESS_ISSUE_TOKEN.getMessage();
		TokenResponseDTO responseDTO = new TokenResponseDTO(TOKEN, TOKEN);
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(message, responseDTO));

		// when
		when(authController.reissueToken(principal)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc.perform(
			post(DEFAULT_URL + "/token")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.principal(principal));

		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(OBJECT).description("응답 데이터"));
		responseFields.add(fieldWithPath("data.accessToken").type(STRING).description("smeem access token"));
		responseFields.add(fieldWithPath("data.refreshToken").type(STRING).description("smeem refresh token"));

		doDocument(resultActions,
			"Token Reissue Test",
			TAG,
			"토큰 재발급",
			responseFields,
			status().isOk());
	}

	@Test
	@DisplayName("로그아웃 테스트")
	@SecurityRequirement(name = "Authorization")
	void success_logout_test() throws Exception {
		// given
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(SUCCESS_SIGNOUT.getMessage()));

		// when
		when(authController.signOut(principal)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc.perform(
			post(DEFAULT_URL + "/sign-out")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.principal(principal));

		doDocumentNullData(resultActions, "Logout Test", TAG, "로그아웃");
	}

	@Test
	@DisplayName("회원 탈퇴 테스트")
	void success_with_drawl_test() throws Exception {
		// given
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(SUCCESS_WITHDRAW.getMessage()));

		// when
		when(authController.withDrawl(principal)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc.perform(
			delete(DEFAULT_URL)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.principal(principal));

		doDocumentNullData(resultActions, "With Drawl Test", TAG, "회원 탈퇴");
	}
}