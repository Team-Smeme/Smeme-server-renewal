package com.smeem.api.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.topic.controller.TopicController;
import com.smeem.api.topic.service.dto.response.RandomTopicGetServiceResponse;
import lombok.val;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.security.Principal;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.smeem.common.code.success.TopicSuccessCode.SUCCESS_GET_RANDOM_TOPIC;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@DisplayName("TopicController 테스트")
//@WebMvcTest(TopicController.class)
class TopicControllerTest extends BaseControllerTest {
    @MockBean
    TopicController topicController;
    @MockBean
    Principal principal;

    private final String DEFAULT_URL = "/api/v2/topics";
    private final String TAG = "Topic";

//    @Test
//    @DisplayName("랜덤 주제 조회 테스트")
    void success_get_random_topic_test() throws Exception {
        // given
        RandomTopicGetServiceResponse response = new RandomTopicGetServiceResponse(1L, "가보고 싶은 해외 여행 지가 있다면 소개해 주세요!");
        val result = ApiResponseUtil.success(SUCCESS_GET_RANDOM_TOPIC, response);

        // when
        when(topicController.getTopicByRandom()).thenReturn(result);

        // then
        mockMvc.perform(get(DEFAULT_URL + "/random")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .principal(principal))
                .andDo(
                        document("랜덤 주제 조회 성공 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(TAG)
                                        .description("랜덤 주제 조회")
                                        .responseFields(
                                                fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("message").type(STRING).description("응답 메시지"),
                                                fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                                                fieldWithPath("data.topicId").type(NUMBER).description("랜덤 주제 id"),
                                                fieldWithPath("data.content").type(STRING).description("랜덤 주제 내용")
                                        )
                                        .build()
                                )
                        ))
                .andExpect(status().isOk());
    }
}