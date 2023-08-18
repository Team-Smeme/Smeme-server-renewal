package com.smeme.server.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static com.smeme.server.util.ApiResponse.*;
import static com.smeme.server.util.message.ResponseMessage.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import java.net.URI;
import java.security.Principal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.smeme.server.dto.correction.CorrectionRequestDTO;
import com.smeme.server.dto.correction.CorrectionResponseDTO;
import com.smeme.server.util.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@DisplayName("CorrectionController 테스트")
@WebMvcTest(CorrectionController.class)
@SecurityRequirement(name = "Authorization")
class CorrectionControllerTest extends BaseControllerTest {

	private static final String DEFAULT_URL = "/api/v2/corrections";

	@MockBean
	CorrectionController correctionController;
	@MockBean
	Principal principal;

	@Test
	@DisplayName("일기 첨삭 테스트")
	void success_create_correction_test() throws Exception {
		// given
		CorrectionRequestDTO requestDTO = new CorrectionRequestDTO("Hello", "Hi");
		CorrectionResponseDTO responseDTO = CorrectionResponseDTO.testOf();
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

		resultActions
			.andDo(document("Create Correction Test",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("Correction")
						.description("일기 첨삭")
						.pathParameters(
							parameterWithName("diaryId").description("첨삭 대상 일기 id")
						)
						.requestFields(
							fieldWithPath("sentence").type(STRING).description("첨삭할 문장"),
							fieldWithPath("content").type(STRING).description("첨삭 내용")
						)
						.responseHeaders(
							headerWithName("Location").description("첨삭 대상 일기 조회 URI")
						)
						.responseFields(
							fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
							fieldWithPath("message").type(STRING).description("응답 메시지"),
							fieldWithPath("data").type(OBJECT).description("응답 데이터"),
							fieldWithPath("data.diaryId").type(NUMBER).description("첨삭 대상 일기 id"),
							fieldWithPath("data.badge").type(OBJECT).description("획득한 뱃지 정보"),
							fieldWithPath("data.badge.id").type(NUMBER).description("뱃지 id"),
							fieldWithPath("data.badge.type").type(STRING).description("뱃지 타입"),
							fieldWithPath("data.badge.name").type(STRING).description("뱃지 이름"),
							fieldWithPath("data.badge.imageUrl").type(STRING).description("뱃지 이미지 url")
						)
						.build())))
			.andExpect(MockMvcResultMatchers.status().isCreated());
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

		resultActions
			.andDo(document("Delete Correction Test",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("Correction")
						.description("첨삭 삭제")
						.pathParameters(
							parameterWithName("correctionId").description("첨삭 id")
						)
						.responseFields(
							fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
							fieldWithPath("message").type(STRING).description("응답 메시지"),
							fieldWithPath("data").type(NULL).description("응답 데이터")
						)
						.build())))
			.andExpect(MockMvcResultMatchers.status().isOk());
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

		resultActions
			.andDo(document("Update Correction Test",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("Correction")
						.description("첨삭 수정")
						.pathParameters(
							parameterWithName("correctionId").description("첨삭 id")
						)
						.requestFields(
							fieldWithPath("sentence").type(STRING).description("첨삭할 문장"),
							fieldWithPath("content").type(STRING).description("첨삭 내용")
						)
						.responseFields(
							fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
							fieldWithPath("message").type(STRING).description("응답 메시지"),
							fieldWithPath("data").type(NULL).description("응답 데이터")
						)
						.build())))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}