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

import com.smeme.server.dto.badge.BadgeListResponseDTO;
import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.model.badge.BadgeType;
import com.smeme.server.util.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@DisplayName("BadgeController 테스트")
@WebMvcTest(BadgeController.class)
@SecurityRequirement(name = "Authorization")
class BadgeControllerTest extends BaseControllerTest {

	private static final String DEFAULT_URL = "/api/v2/members/badges";
	private final String TAG = "Badge";

	@MockBean
	BadgeController badgeController;
	@MockBean
	Principal principal;

	@Test
	@DisplayName("사용자 뱃지 조회 테스트")
	void success_get_member_badge_test() throws Exception {
		// given
		List<BadgeResponseDTO> badges = List.of(new BadgeResponseDTO(1L, "연속 3일 일기 뱃지",
			BadgeType.COMBO, "https://m.s3.ap-northeast-2.amazonaws.com/badge/streak.png"));
		BadgeListResponseDTO responseDTO = new BadgeListResponseDTO(badges);
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(SUCCESS_GET_BADGES.getMessage(), responseDTO));

		// when
		when(badgeController.getBadgeList(principal)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(get(DEFAULT_URL)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.principal(principal));

		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(OBJECT).description("응답 데이터"));
		responseFields.add(fieldWithPath("data.badges[]").type(ARRAY).description("뱃지 정보 리스트"));
		responseFields.add(fieldWithPath("data.badges[].id").type(NUMBER).description("뱃지 id"));
		responseFields.add(fieldWithPath("data.badges[].name").type(STRING).description("뱃지 이름"));
		responseFields.add(fieldWithPath("data.badges[].type").type(STRING).description("뱃지 타입"));
		responseFields.add(fieldWithPath("data.badges[].imageUrl").type(STRING).description("뱃지 이미지 url"));

		doDocument(resultActions,
			"Get Member's Badges Test",
			TAG,
			"회원 뱃지 조회",
			responseFields,
			status().isOk());
	}

}