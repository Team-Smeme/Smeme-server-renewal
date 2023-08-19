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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.epages.restdocs.apispec.ParameterDescriptorWithType;
import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.dto.member.MemberGetResponseDTO;
import com.smeme.server.dto.member.MemberNameResponseDTO;
import com.smeme.server.dto.member.MemberPlanUpdateRequestDTO;
import com.smeme.server.dto.member.MemberUpdateRequestDTO;
import com.smeme.server.dto.member.MemberUpdateResponseDTO;
import com.smeme.server.dto.training.TrainingTimeRequestDTO;
import com.smeme.server.dto.training.TrainingTimeResponseDTO;
import com.smeme.server.model.badge.BadgeType;
import com.smeme.server.model.goal.GoalType;
import com.smeme.server.util.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@DisplayName("MemberController 테스트")
@WebMvcTest(MemberController.class)
@SecurityRequirement(name = "Authorization")
class MemberControllerTest extends BaseControllerTest {

	private static final String DEFAULT_URL = "/api/v2/members";
	private final String TAG = "Member";

	@MockBean
	MemberController memberController;
	@MockBean
	Principal principal;

	@Test
	@DisplayName("회원 정보 수정 테스트")
	void success_update_member_test() throws Exception {
		// given
		MemberUpdateRequestDTO requestDTO = new MemberUpdateRequestDTO("홍길동", false);
		String badgeName = "웰컴 뱃지";
		String imageUrl = "https://m.s3.ap-northeast-2.amazonaws.com/badge/welcome.png";
		MemberUpdateResponseDTO responseDTO = new MemberUpdateResponseDTO(
			List.of(new MemberUpdateResponseDTO.BadgeDTO(badgeName, imageUrl)));
		String message = SUCCESS_UPDATE_USERNAME.getMessage();
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(message, responseDTO));

		// when
		when(memberController.updateUserProfile(principal, requestDTO)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(patch(DEFAULT_URL)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.principal(principal)
				.content(objectMapper.writeValueAsString(requestDTO)));

		List<FieldDescriptor> requestFields = new ArrayList<>();
		requestFields.add(fieldWithPath("username").type(STRING).description("회원 이름"));
		requestFields.add(fieldWithPath("termAccepted").type(BOOLEAN).description("정책 동의 여부"));

		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(OBJECT).description("응답 데이터"));
		responseFields.add(fieldWithPath("data.badges[]").type(ARRAY).description("뱃지 리스트"));
		responseFields.add(fieldWithPath("data.badges[].name").type(STRING).description("뱃지 이름"));
		responseFields.add(fieldWithPath("data.badges[].imageUrl").type(STRING).description("뱃지 이미지 url"));

		doDocument(resultActions,
			"Update Member Profile",
			TAG,
			"회원 정보 수정",
			requestFields,
			responseFields,
			status().isOk());
	}

	@Test
	@DisplayName("회원 정보 조회 테스트")
	void success_get_member_test() throws Exception {
		// given
		String imageUrl = "https://m.s3.ap-northeast-2.amazonaws.com/badge/welcome.png";
		MemberGetResponseDTO responseDTO = new MemberGetResponseDTO(
			"test",
			"자기계발",
			"주 5회 이상 오늘 하루를 돌아보는 일기 작성하기",
			"사전 없이 일기 완성\nsmeem 연속 일기 배지 획득",
			"en",
			true,
			new TrainingTimeResponseDTO("월", 13, 30),
			new BadgeResponseDTO(1L, "웰컴 뱃지", BadgeType.EVENT.toString(), imageUrl)
		);
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(SUCCESS_GET_USER.getMessage(), responseDTO));

		// when
		when(memberController.getUserProfile(principal)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(get(DEFAULT_URL + "/me")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.principal(principal));

		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(OBJECT).description("응답 데이터"));
		responseFields.add(fieldWithPath("data.username").type(STRING).description("닉네임"));
		responseFields.add(fieldWithPath("data.target").type(STRING).description("학습 목표"));
		responseFields.add(fieldWithPath("data.way").type(STRING).description("목표 달성 방법"));
		responseFields.add(fieldWithPath("data.detail").type(STRING).description("목표 세부 내용"));
		responseFields.add(fieldWithPath("data.targetLang").type(STRING).description("목표 언어"));
		responseFields.add(fieldWithPath("data.hasPushAlarm").type(BOOLEAN).description("푸시 알림 여부"));
		responseFields.add(fieldWithPath("data.trainingTime").type(OBJECT).description("학습 시간 정보"));
		responseFields.add(fieldWithPath("data.trainingTime.day").type(STRING).description("학습 시간 요일"));
		responseFields.add(fieldWithPath("data.trainingTime.hour").type(NUMBER).description("학습 시간 시간"));
		responseFields.add(fieldWithPath("data.trainingTime.minute").type(NUMBER).description("학습 시간 분"));
		responseFields.add(fieldWithPath("data.badge").type(OBJECT).description("뱃지 정보"));
		responseFields.add(fieldWithPath("data.badge.id").type(NUMBER).description("뱃지 id"));
		responseFields.add(fieldWithPath("data.badge.name").type(STRING).description("뱃지 이름"));
		responseFields.add(fieldWithPath("data.badge.type").type(STRING).description("뱃지 타입"));
		responseFields.add(fieldWithPath("data.badge.imageUrl").type(STRING).description("뱃지 이미지 URL"));

		doDocument(resultActions,
			"Get Member Profile Test",
			TAG,
			"회원 정보 조회",
			responseFields,
			status().isOk());
	}

	@Test
	@DisplayName("회원 학습 계획 수정 테스트")
	void success_update_member_plan_test() throws Exception {
		// given
		MemberPlanUpdateRequestDTO requestDTO = new MemberPlanUpdateRequestDTO(
			GoalType.HOBBY,
			new TrainingTimeRequestDTO("월", 13, 30),
			true
		);
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(SUCCESS_UPDATE_USER_PLAN.getMessage()));

		// when
		when(memberController.updateUserPlan(principal, requestDTO)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(patch(DEFAULT_URL + "/plan")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.principal(principal)
				.content(objectMapper.writeValueAsString(requestDTO)));

		List<FieldDescriptor> requestFields = new ArrayList<>();
		requestFields.add(fieldWithPath("target").type(STRING).description("학습 목표 타입"));
		requestFields.add(fieldWithPath("trainingTime").type(OBJECT).description("학습 시간"));
		requestFields.add(fieldWithPath("trainingTime.day").type(STRING).description("학습 시간 요일"));
		requestFields.add(fieldWithPath("trainingTime.hour").type(NUMBER).description("학습 시간 시간"));
		requestFields.add(fieldWithPath("trainingTime.minute").type(NUMBER).description("학습 시간 분"));
		requestFields.add(fieldWithPath("hasAlarm").type(BOOLEAN).description("푸시 알람 동의 여부"));

		doDocument(resultActions,
			"Update Member Plan Test",
			TAG,
			"회원 학습 계획 수정",
			requestFields,
			getNullDataResponseFields(),
			status().isOk());
	}

	@Test
	@DisplayName("닉네임 중복 체크 테스트")
	void success_check_duplicated_nickname_test() throws Exception {
		// given
		String username = "스미미";
		MultiValueMap<String, String> queries = new LinkedMultiValueMap<>();
		queries.add("name", username);
		MemberNameResponseDTO responseDTO = new MemberNameResponseDTO(true);
		String message = SUCCESS_CHECK_DUPLICATED_NAME.getMessage();
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(message, responseDTO));

		// when
		when(memberController.checkDuplicatedName(username)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(get(DEFAULT_URL + "/nickname/check")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.principal(principal)
				.params(queries));

		List<ParameterDescriptorWithType> parameters = new ArrayList<>();
		parameters.add(parameterWithName("name").description("유저 닉네임"));

		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(OBJECT).description("응답 데이터"));
		responseFields.add(fieldWithPath("data.isExist").type(BOOLEAN).description("회원 이름 존재 여부"));

		getDocumentQueryParam(resultActions,
			"Check Duplicated Name Test",
			TAG,
			"닉네임 중복 체크",
			parameters,
			responseFields,
			status().isOk());
	}

}