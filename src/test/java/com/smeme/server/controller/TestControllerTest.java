package com.smeme.server.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.smeme.server.util.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@WebMvcTest(TestController.class)
@ActiveProfiles("local")
public class TestControllerTest extends BaseControllerTest {

    private static final String DEFAULT_URL = "/api/v2/test";

    @MockBean
    TestController testController;

    @Test
    @DisplayName("서버 연결 테스트")
    void success_test() throws Exception {

        //given
        when(testController.test())
                .thenReturn(ResponseEntity.ok(ApiResponse.success("server connect", null)));

        //then
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get(DEFAULT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(
                MockMvcRestDocumentation.document(
                        "test-docs",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .description("서버 연결 테스트")
                                        .requestFields()
                                        .responseFields(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터")
                                        ).build()
                )
            )
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}

