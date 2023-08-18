package com.smeme.server.controller;

import com.smeme.server.util.ApiResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;

import static com.smeme.server.controller.Util.*;
import static com.smeme.server.util.ApiResponse.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import java.security.Principal;

@DisplayName("TestController 테스트")
@WebMvcTest(TestController.class)
class TestControllerTest extends BaseControllerTest {

	private static final String DEFAULT_URL = "/api/v2/test";
	private final String TAG = "Test";

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

		doDocumentNullData(resultActions, "Server Connect Test", TAG, "서버 연결 테스트");
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

		doDocumentNullData(resultActions, "Push Alarm Test", TAG, "푸시 알림 테스트");
	}
}

