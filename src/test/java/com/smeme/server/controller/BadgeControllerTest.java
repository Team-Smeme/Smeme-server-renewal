package com.smeme.server.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.smeme.server.fixture.badge.BadgeFixture;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.smeme.server.util.ApiResponse.success;
import static com.smeme.server.util.message.ResponseMessage.SUCCESS_GET_BADGES;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("BadgeController 테스트")
@WebMvcTest(BadgeController.class)
public class BadgeControllerTest extends BaseControllerTest {

    @MockBean
    BadgeController badgeController;
    @MockBean
    Principal principal;

    private final String DEFAULT_URL = "/api/v2/members/badges";
    private final String TAG = "Badge";

    @DisplayName("뱃지 목록 조회 API 성공")
    @Test
    void success_getBadgesList() throws Exception {
        val resources = ResourceSnippetParameters.builder()
                .tag(TAG)
                .description("뱃지 목록 조회")
                .responseFields(
                        fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                        fieldWithPath("data.badges").type(ARRAY).description("뱃지 목록"),
                        fieldWithPath("data.badges[].id").type(NUMBER).description("뱃지 ID"),
                        fieldWithPath("data.badges[].name").type(STRING).description("뱃지 이름"),
                        fieldWithPath("data.badges[].type").type(STRING).description("뱃지 타입"),
                        fieldWithPath("data.badges[].imageUrl").type(STRING).description("뱃지 이미지 URL")
                )
                .build();
        // when
        val result = ResponseEntity.ok(success(SUCCESS_GET_BADGES.getMessage(), BadgeFixture.createBadgeListResponseDTO()));

        when(badgeController.getBadgeList(principal))
                .thenReturn(result);


        mockMvc.perform(get(DEFAULT_URL)
                        .contentType(APPLICATION_JSON)
                        .principal(principal))
                .andDo(
                        document("뱃지 목록 조회 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(resources)
                        ))
                .andExpect(status().isOk());
    }

}
