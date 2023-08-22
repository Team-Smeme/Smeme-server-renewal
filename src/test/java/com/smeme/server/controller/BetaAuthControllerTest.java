package com.smeme.server.controller;

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

import com.smeme.server.dto.auth.beta.BetaSignInRequestDTO;
import com.smeme.server.dto.auth.beta.BetaTokenResponseDTO;
import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.model.badge.BadgeType;
import com.smeme.server.util.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@DisplayName("BetaAuthController 테스트")
@WebMvcTest(BetaAuthController.class)
@SecurityRequirement(name = "Authorization")
class BetaAuthControllerTest extends BaseControllerTest {

	private static final String DEFAULT_URL = "/api/beta";
	private final String TAG = "Beta";

	@MockBean
	BetaAuthController betaAuthController;
	@MockBean
	Principal principal;

	@Test
	@DisplayName("베타 테스트 임시 토큰 발급 테스트")
	void success_get_beta_token_test() throws Exception {
		// given
		BetaSignInRequestDTO requestDTO = new BetaSignInRequestDTO("dsdsdsdsds");
		BetaTokenResponseDTO responseDTO = new BetaTokenResponseDTO(
			"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIyMjU0NjY4LCJleHAiO",
			List.of(new BadgeResponseDTO(1L, "웰컴 뱃지", BadgeType.EVENT, "image-url"))
		);
		String message = SUCCESS_BETA_AUTH_TOKEN.getMessage();
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(message, responseDTO));

		// when
		when(betaAuthController.getToken(requestDTO)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(post(DEFAULT_URL + "/token")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDTO)));

		List<FieldDescriptor> requestFields = new ArrayList<>();
		requestFields.add(fieldWithPath("fcmToken").type(STRING).description("FCM 토큰"));

		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(OBJECT).description("응답 데이터"));
		responseFields.add(fieldWithPath("data.accessToken").type(STRING).description("smeem beta access token"));
		responseFields.add(fieldWithPath("data.badges[]").type(ARRAY).description("뱃지 리스트"));
		responseFields.add(fieldWithPath("data.badges[].id").type(NUMBER).description("뱃지 id"));
		responseFields.add(fieldWithPath("data.badges[].name").type(STRING).description("뱃지 이름"));
		responseFields.add(fieldWithPath("data.badges[].type").type(STRING).description("뱃지 타입"));
		responseFields.add(fieldWithPath("data.badges[].imageUrl").type(STRING).description("뱃지 이미지 URL"));

		doDocument(resultActions,
			"Get Beta Token Test",
			TAG,
			"베타 테스트 토큰 발급",
			requestFields,
			responseFields,
			status().isOk());
	}

}