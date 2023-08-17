package com.smeme.server.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.smeme.server.util.ApiResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.smeme.server.util.ApiResponse.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.security.Principal;

@DisplayName("TestController 테스트")
@WebMvcTest(TestController.class)
class TestControllerTest extends BaseControllerTest {

	private static final String DEFAULT_URL = "/api/v2/test";

	@MockBean
	TestController testController;
	@MockBean
	Principal principal;

	@Test
	@DisplayName("서버 연결 테스트")
	void success_server_connect_test() throws Exception {
		//given
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success("server connect"));

		// when
		when(testController.test()).thenReturn(response);

		//then
		ResultActions resultActions = mockMvc
			.perform(get(DEFAULT_URL)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON));

		resultActions
			.andDo(document("server connect test",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.description("서버 연결 테스트")
						.requestFields()
						.responseFields(
							fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
							fieldWithPath("message").type(STRING).description("응답 메시지"),
							fieldWithPath("data").type(NULL).description("응답 데이터"))
						.build())))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@DisplayName("푸시 알림 테스트")
	void success_push_alarm_test() throws Exception {
		// given
		ResponseEntity<ApiResponse> response = ResponseEntity.ok(success("푸시 알림 전송 성공"));

		// when
		when(testController.alarmTest(principal)).thenReturn(response);

		// then
		ResultActions resultActions = mockMvc
			.perform(get(DEFAULT_URL + "/alarm")
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.principal(principal));

		resultActions
			.andDo(document("push alarm test",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(
					ResourceSnippetParameters.builder()
						.description("푸시 알림 테스트")
						.requestFields()
						.responseFields(
							fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
							fieldWithPath("message").type(STRING).description("응답 메시지"),
							fieldWithPath("data").type(NULL).description("응답 데이터"))
						.build())))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}

