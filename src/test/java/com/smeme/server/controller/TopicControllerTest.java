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

import java.security.Principal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.smeme.server.dto.topic.TopicResponseDTO;
import com.smeme.server.util.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@DisplayName("TopicController 테스트")
@WebMvcTest(TopicController.class)
@SecurityRequirement(name = "Authorization")
class TopicControllerTest extends BaseControllerTest {

	private static final String DEFAULT_URL = "/api/v2/topics";

	@MockBean
	TopicController topicController;
	@MockBean
	Principal principal;

	@Test
	@DisplayName("랜덤 주제 조회 테스트")
	void success_get_random_topic_test() throws Exception {
		// given
		TopicResponseDTO responseDTO = new TopicResponseDTO(1L, "가보고 싶은 해외 여행지가 있다면 소개해 주세요!");
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(
			success(SUCCESS_GET_RANDOM_TOPIC.getMessage(), responseDTO));

		// when
		when(topicController.getRandomTopic()).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(get(DEFAULT_URL + "/random")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.principal(principal));

		resultActions
			.andDo(document("Get Random Topic Test",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.tag("Topic")
						.description("랜덤 주제 조회")
						.responseFields(
							fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
							fieldWithPath("message").type(STRING).description("응답 메시지"),
							fieldWithPath("data").type(OBJECT).description("응답 데이터"),
							fieldWithPath("data.topicId").type(NUMBER).description("랜덤 주제 id"),
							fieldWithPath("data.content").type(STRING).description("랜덤 주제 내용")
						)
						.build())))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

}