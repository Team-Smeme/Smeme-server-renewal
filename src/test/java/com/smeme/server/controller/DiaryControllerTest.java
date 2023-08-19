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

import java.net.URI;
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

import com.epages.restdocs.apispec.HeaderDescriptorWithType;
import com.epages.restdocs.apispec.ParameterDescriptorWithType;
import com.smeme.server.dto.diary.CreatedDiaryResponseDTO;
import com.smeme.server.dto.diary.DiariesResponseDTO;
import com.smeme.server.dto.diary.DiaryRequestDTO;
import com.smeme.server.dto.diary.DiaryResponseDTO;
import com.smeme.server.util.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@DisplayName("DiaryController 테스트")
@WebMvcTest(DiaryController.class)
@SecurityRequirement(name = "Authorization")
class DiaryControllerTest extends BaseControllerTest {
	private static final String DEFAULT_URL = "/api/v2/diaries";
	private final String TAG = "Diary";

	@MockBean
	DiaryController diaryController;
	@MockBean
	Principal principal;

	@Test
	@DisplayName("일기 생성 테스트")
	void success_create_diary_test() throws Exception {
		// given
		DiaryRequestDTO requestDTO = new DiaryRequestDTO("Hello SMEEM!", 1L);
		CreatedDiaryResponseDTO responseDTO = new CreatedDiaryResponseDTO(
			1L,
			List.of(new CreatedDiaryResponseDTO.BadgeDTO("웰컴 뱃지", "image-url"))
		);
		ResponseEntity<ApiResponse> response = ResponseEntity
			.created(URI.create("~/api/v2/diaries/1"))
			.body(success(SUCCESS_CREATE_DIARY.getMessage(), responseDTO));

		// when
		when(diaryController.createDiary(principal, requestDTO)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(post(DEFAULT_URL)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.principal(principal)
				.content(objectMapper.writeValueAsString(requestDTO)));

		List<FieldDescriptor> requestFields = new ArrayList<>();
		requestFields.add(fieldWithPath("content").type(STRING).description("일기 내용"));
		requestFields.add(fieldWithPath("topicId").type(NUMBER).description("일기 랜덤주제 id"));

		List<HeaderDescriptorWithType> responseHeaders = new ArrayList<>();
		responseHeaders.add(headerWithName("Location").description("생성된 일기 조회 URI"));

		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(OBJECT).description("응답 데이터"));
		responseFields.add(fieldWithPath("data.diaryId").type(NUMBER).description("생성한 일기 id"));
		responseFields.add(fieldWithPath("data.badges[]").type(ARRAY).description("획득한 뱃지 리스트"));
		responseFields.add(fieldWithPath("data.badges[].name").type(STRING).description("뱃지 이름"));
		responseFields.add(fieldWithPath("data.badges[].imageUrl").type(STRING).description("뱃지 이미지 url"));

		doDocument(resultActions,
			"Create Diary Test",
			TAG,
			"일기 작성",
			new ArrayList<>(),
			requestFields,
			responseHeaders,
			responseFields,
			status().isCreated());
	}

	@Test
	@DisplayName("일기 조회 테스트")
	void success_get_diary_detail_test() throws Exception {
		// given
		DiaryResponseDTO responseDTO = new DiaryResponseDTO(
			1L,
			"가보고 싶은 해외 여행지가 있다면 소개해 주세요!",
			"I want to go Bla Bla ~",
			"2023-08-19 14:00",
			"스미무",
			List.of(new DiaryResponseDTO.CorrectionDTO(1L, "Hello", "Hi"))
		);
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(SUCCESS_GET_DIARY.getMessage(), responseDTO));

		// when
		when(diaryController.getDiaryDetail(anyLong())).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(get(DEFAULT_URL + "/{diaryId}", 1L)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON));

		List<ParameterDescriptorWithType> parameters = new ArrayList<>();
		parameters.add(parameterWithName("diaryId").description("일기 id"));

		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(OBJECT).description("응답 데이터"));
		responseFields.add(fieldWithPath("data.diaryId").type(NUMBER).description("일기 id"));
		responseFields.add(fieldWithPath("data.topic").type(STRING).description("랜덤 주제 내용"));
		responseFields.add(fieldWithPath("data.content").type(STRING).description("일기 내용"));
		responseFields.add(fieldWithPath("data.createdAt").type(STRING).description("작성 날짜"));
		responseFields.add(fieldWithPath("data.username").type(STRING).description("작성자 이름"));
		responseFields.add(fieldWithPath("data.corrections[]").type(ARRAY).description("첨삭 리스트"));
		responseFields.add(fieldWithPath("data.corrections[].correctionId").type(NUMBER).description("첨삭 id"));
		responseFields.add(fieldWithPath("data.corrections[].before").type(STRING).description("첨삭 전 내용"));
		responseFields.add(fieldWithPath("data.corrections[].after").type(STRING).description("첨삭 후 내용"));

		getDocumentPathParam(resultActions,
			"Get Diary Test",
			TAG,
			"일기 조회",
			parameters,
			responseFields,
			status().isOk());
	}

	@Test
	@DisplayName("일기 수정 테스트")
	void success_update_diary_test() throws Exception {
		// given
		DiaryRequestDTO requestDTO = new DiaryRequestDTO("Hello SMEEM!", 1L);
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(SUCCESS_UPDATE_DAIRY.getMessage()));
		Long diaryId = 1L;

		// when
		when(diaryController.updateDiary(diaryId, requestDTO)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(patch(DEFAULT_URL + "/{diaryId}", diaryId)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDTO)));

		List<ParameterDescriptorWithType> parameters = new ArrayList<>();
		parameters.add(parameterWithName("diaryId").description("일기 id"));

		List<FieldDescriptor> requestFields = new ArrayList<>();
		requestFields.add(fieldWithPath("content").type(STRING).description("수정할 일기 내용"));
		requestFields.add(fieldWithPath("topicId").type(NUMBER).description("랜덤 주제 id"));

		patchDocumentPathParam(resultActions,
			"Update Diary Test",
			TAG,
			"일기 수정",
			parameters,
			requestFields,
			getNullDataResponseFields(),
			status().isOk());
	}

	@Test
	@DisplayName("일기 삭제 테스트")
	void success_delete_diary_test() throws Exception {
		// given
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(SUCCESS_DELETE_DIARY.getMessage()));
		Long diaryId = 1L;

		// when
		when(diaryController.deleteDiary(diaryId)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(delete(DEFAULT_URL + "/{diaryId}", diaryId)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON));

		List<ParameterDescriptorWithType> parameters = new ArrayList<>();
		parameters.add(parameterWithName("diaryId").description("일기 id"));

		deleteDocumentPathParam(resultActions,
			"Delete Diary Test",
			TAG,
			"일기 삭제",
			parameters,
			getNullDataResponseFields(),
			status().isOk());
	}

	@Test
	@DisplayName("일기 리스트 조회 테스트")
	void success_get_diaries_test() throws Exception {
		// given
		String message = SUCCESS_GET_DIARIES.getMessage();
		DiariesResponseDTO responseDTO = new DiariesResponseDTO(
			List.of(new DiariesResponseDTO.DiaryDTO(1L, "Hello Smeem!", "2023-08-19 14:00")),
			true
		);
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(message, responseDTO));

		MultiValueMap<String, String> queries = new LinkedMultiValueMap<>();
		String startDate = "2023-08-18 00:00";
		String endDate = "2023-09-18 00:00";
		queries.add("start", startDate);
		queries.add("end", endDate);

		// when
		when(diaryController.getDiaries(principal, startDate, endDate)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(get(DEFAULT_URL)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.principal(principal)
				.params(queries));

		List<ParameterDescriptorWithType> parameters = new ArrayList<>();
		parameters.add(parameterWithName("start").description("범위 시작 날짜(yyyy-MM-dd HH:mm)"));
		parameters.add(parameterWithName("end").description("범위 끝 날짜(yyyy-MM-dd HH:mm)"));

		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(OBJECT).description("응답 데이터"));
		responseFields.add(fieldWithPath("data.diaries[]").type(ARRAY).description("일기 정보 리스트"));
		responseFields.add(fieldWithPath("data.diaries[].diaryId").type(NUMBER).description("일기 id"));
		responseFields.add(fieldWithPath("data.diaries[].content").type(STRING).description("일기 내용"));
		responseFields.add(fieldWithPath("data.diaries[].createdAt").type(STRING).description("일기 작성 일자"));
		responseFields.add(fieldWithPath("data.has30Past").type(BOOLEAN).description("30일 전 일기 존재 여부"));

		getDocumentQueryParam(resultActions,
			"Get Diaries Test",
			TAG,
			"일기 리스트 조회",
			parameters,
			responseFields,
			status().isOk());
	}
}