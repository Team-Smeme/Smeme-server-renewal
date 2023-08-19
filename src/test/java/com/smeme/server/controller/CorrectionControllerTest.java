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

import com.epages.restdocs.apispec.HeaderDescriptorWithType;
import com.epages.restdocs.apispec.ParameterDescriptorWithType;
import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.dto.correction.CorrectionRequestDTO;
import com.smeme.server.dto.correction.CorrectionResponseDTO;
import com.smeme.server.model.badge.BadgeType;
import com.smeme.server.util.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@DisplayName("CorrectionController 테스트")
@WebMvcTest(CorrectionController.class)
@SecurityRequirement(name = "Authorization")
class CorrectionControllerTest extends BaseControllerTest {

	private static final String DEFAULT_URL = "/api/v2/corrections";
	private final String TAG = "Correction";

	@MockBean
	CorrectionController correctionController;
	@MockBean
	Principal principal;

	@Test
	@DisplayName("일기 첨삭 테스트")
	void success_create_correction_test() throws Exception {
		// given
		CorrectionRequestDTO requestDTO = new CorrectionRequestDTO("Hello", "Hi");
		CorrectionResponseDTO responseDTO = new CorrectionResponseDTO(
			1L,
			new BadgeResponseDTO(1L, "웰컴 뱃지", BadgeType.EVENT, "image-url")
		);
		ResponseEntity<ApiResponse> response = ResponseEntity
			.created(URI.create("~/api/v2/diaries/1"))
			.body(success(SUCCESS_CREATE_CORRECTION.getMessage(), responseDTO));
		Long diaryId = 1L;

		// when
		when(correctionController.createCorrection(principal, diaryId, requestDTO)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(post(DEFAULT_URL + "/diary/{diaryId}", diaryId)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.principal(principal)
				.content(objectMapper.writeValueAsString(requestDTO)));

		List<ParameterDescriptorWithType> parameters = new ArrayList<>();
		parameters.add(parameterWithName("diaryId").description("첨삭 대상 일기 id"));

		List<FieldDescriptor> requestFields = new ArrayList<>();
		requestFields.add(fieldWithPath("sentence").type(STRING).description("첨삭할 문장"));
		requestFields.add(fieldWithPath("content").type(STRING).description("첨삭 내용"));

		List<HeaderDescriptorWithType> responseHeaders = new ArrayList<>();
		responseHeaders.add(headerWithName("Location").description("첨삭 대상 일기 조회 URI"));

		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(OBJECT).description("응답 데이터"));
		responseFields.add(fieldWithPath("data.diaryId").type(NUMBER).description("첨삭 대상 일기 id"));
		responseFields.add(fieldWithPath("data.badge").type(OBJECT).description("획득한 뱃지 정보"));
		responseFields.add(fieldWithPath("data.badge.id").type(NUMBER).description("뱃지 id"));
		responseFields.add(fieldWithPath("data.badge.type").type(STRING).description("뱃지 타입"));
		responseFields.add(fieldWithPath("data.badge.name").type(STRING).description("뱃지 이름"));
		responseFields.add(fieldWithPath("data.badge.imageUrl").type(STRING).description("뱃지 이미지 url"));

		doDocument(resultActions,
			"Create Correction Test",
			TAG,
			"일기 첨삭",
			parameters,
			new ArrayList<>(),
			requestFields,
			responseHeaders,
			responseFields,
			status().isCreated());
	}

	@Test
	@DisplayName("첨삭 삭제 테스트")
	void success_delete_correction_test() throws Exception {
		// given
		Long correctionId = 1L;
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(SUCCESS_DELETE_CORRECTION.getMessage()));

		// when
		when(correctionController.deleteCorrection(correctionId)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(delete(DEFAULT_URL + "/{correctionId}", correctionId)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON));

		List<ParameterDescriptorWithType> parameters = new ArrayList<>();
		parameters.add(parameterWithName("correctionId").description("첨삭 id"));

		deleteDocumentPathParam(resultActions,
			"Delete Correction Test",
			TAG,
			"첨삭 삭제",
			parameters,
			getNullDataResponseFields(),
			status().isOk());
	}

	@Test
	@DisplayName("첨삭 수정 테스트")
	void success_update_correction_test() throws Exception {
		// given
		Long correctionId = 1L;
		CorrectionRequestDTO requestDTO = new CorrectionRequestDTO("Hello", "Hi");
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success(SUCCESS_UPDATE_CORRECTION.getMessage()));

		// when
		when(correctionController.updateCorrection(correctionId, requestDTO)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(patch(DEFAULT_URL + "/{correctionId}", correctionId)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDTO)));

		List<ParameterDescriptorWithType> parameters = new ArrayList<>();
		parameters.add(parameterWithName("correctionId").description("첨삭 id"));

		List<FieldDescriptor> requestFields = new ArrayList<>();
		requestFields.add(fieldWithPath("sentence").type(STRING).description("첨삭할 문장"));
		requestFields.add(fieldWithPath("content").type(STRING).description("첨삭 내용"));

		patchDocumentPathParam(resultActions,
			"Update Correction Test",
			TAG,
			"첨삭 수정",
			parameters,
			requestFields,
			getNullDataResponseFields(),
			status().isOk());
	}
}