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

import com.epages.restdocs.apispec.ParameterDescriptorWithType;
import com.smeme.server.dto.goal.GoalResponseDTO;
import com.smeme.server.dto.goal.GoalsResponseDTO;
import com.smeme.server.model.goal.GoalType;
import com.smeme.server.util.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@DisplayName("GoalController 테스트")
@WebMvcTest(GoalController.class)
@SecurityRequirement(name = "Authorization")
class GoalControllerTest extends BaseControllerTest {

	private static final String DEFAULT_URL = "/api/v2/goals";
	private final String TAG = "Goal";

	@MockBean
	GoalController goalController;
	@MockBean
	Principal principal;

	@Test
	@DisplayName("학습 목표 전체 조회 테스트")
	void success_get_all_goals_test() throws Exception {
		// given
		GoalsResponseDTO responseDTO = new GoalsResponseDTO(
			List.of(new GoalsResponseDTO.GoalResponseDTO(GoalType.APPLY, "현지 언어 체득"))
		);
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(SUCCESS_GET_GOALS.getMessage(), responseDTO));

		// when
		when(goalController.getAllGoals()).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(get(DEFAULT_URL)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON));

		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(OBJECT).description("응답 데이터"));
		responseFields.add(fieldWithPath("data.goals[]").type(ARRAY).description("학습 목표 리스트"));
		responseFields.add(fieldWithPath("data.goals[].goalType").type(STRING).description("학습 목표 ENUM 값"));
		responseFields.add(fieldWithPath("data.goals[].name").type(STRING).description("학습 목표 이름"));

		doDocument(resultActions,
			"Get All Goals Test",
			TAG,
			"학습 목표 전체 조회",
			responseFields,
			status().isOk());
	}

	@Test
	@DisplayName("학습 목표 조회 테스트")
	void success_get_goal_test() throws Exception {
		// given
		GoalType type = GoalType.APPLY;
		GoalResponseDTO responseDTO = new GoalResponseDTO("현지 언어 체득",
			"주 5회 이상 오늘 하루를 돌아보는 일기 작성하기", "사전 없이 일기 완성\nsmeem 연속 일기 배지 획득");
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(SUCCESS_GET_GOAL.getMessage(), responseDTO));

		// when
		when(goalController.getGoalByType(type)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(get(DEFAULT_URL + "/{type}", type)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON));

		List<ParameterDescriptorWithType> parameters = new ArrayList<>();
		parameters.add(parameterWithName("type").description("학습 목표 ENUM 값"));

		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(OBJECT).description("응답 데이터"));
		responseFields.add(fieldWithPath("data.name").type(STRING).description("학습 목표 이름"));
		responseFields.add(fieldWithPath("data.way").type(STRING).description("학습 목표 방식"));
		responseFields.add(fieldWithPath("data.detail").type(STRING).description("학습 목표 내용"));

		getDocumentPathParam(resultActions,
			"Get Goal Test",
			TAG,
			"학습 목표 조회",
			parameters,
			responseFields,
			status().isOk());
	}

}