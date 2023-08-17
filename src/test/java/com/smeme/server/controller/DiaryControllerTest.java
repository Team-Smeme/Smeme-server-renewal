package com.smeme.server.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static com.smeme.server.util.ApiResponse.*;
import static com.smeme.server.util.Util.*;
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
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.smeme.server.dto.diary.CreatedDiaryResponseDTO;
import com.smeme.server.dto.diary.DiaryRequestDTO;
import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.BadgeType;
import com.smeme.server.util.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Diary", description = "일기 API")
@DisplayName("DiaryController 테스트")
@WebMvcTest(DiaryController.class)
@SecurityRequirement(name = "Authorization")
class DiaryControllerTest extends BaseControllerTest {

	private static final String DEFAULT_URL = "/api/v2/diaries";

	@MockBean
	DiaryController diaryController;

	@MockBean
	Principal principal;

	@Test
	@DisplayName("일기 생성 테스트")
	void success_create_diary_test() throws Exception {
		// given
		DiaryRequestDTO requestDTO = new DiaryRequestDTO("Hello SMEEM!", 1L);
		CreatedDiaryResponseDTO responseDTO = CreatedDiaryResponseDTO.of(1L, getTestBadges());
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

		resultActions
			.andDo(document("create diary test",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.description("일기 작성 테스트")
						.requestFields(
							fieldWithPath("content").type(STRING).description("일기 내용"),
							fieldWithPath("topicId").type(NUMBER).description("일기 랜덤주제 id")
						)
						.responseHeaders(
							headerWithName("Location").type(SimpleType.STRING).description("생성된 일기 조회 URI")
						)
						.responseFields(
							fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
							fieldWithPath("message").type(STRING).description("응답 메시지"),
							fieldWithPath("data").type(OBJECT).description("응답 데이터"),
							fieldWithPath("data.diaryId").type(NUMBER).description("생성한 일기 id"),
							fieldWithPath("data.badges[]").type(ARRAY).description("획득한 뱃지 리스트"),
							fieldWithPath("data.badges[].name").type(STRING).description("뱃지 이름"),
							fieldWithPath("data.badges[].imageUrl").type(STRING).description("뱃지 이미지 URL")
							)
						.build())))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	private List<Badge> getTestBadges() {
		ArrayList<Badge> badges = new ArrayList<>();
		badges.add(getTestBadge());
		return badges;
	}

	private Badge getTestBadge() {
		return new Badge(BadgeType.EVENT, "웰컴 뱃지", "image-url");
	}

}