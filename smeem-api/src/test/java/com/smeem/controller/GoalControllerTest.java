package com.smeem.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.smeme.server.dto.goal.GoalResponseDTO;
import com.smeme.server.dto.goal.GoalsResponseDTO;
import com.smeme.server.dto.goal.GoalsResponseDTO.GoalResponseVO;
import com.smeme.server.model.goal.GoalType;
import com.smeme.server.util.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.smeme.server.util.ApiResponse.success;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("GoalController 테스트")
@WebMvcTest(GoalController.class)
class GoalControllerTest extends BaseControllerTest {
    @MockBean
    GoalController goalController;
    @MockBean
    Principal principal;

    private final String DEFAULT_URL = "/api/v2/goals";
    private final String TAG = "Goal";

    @Test
    @DisplayName("전체 학습 목표 조회 테스트")
    void success_get_goals_test() throws Exception {
        // given
        GoalsResponseDTO response = new GoalsResponseDTO(goals());
        ResponseEntity<ApiResponse> result = ResponseEntity.ok(success("전체 학습 목표 조회 성공", response));

        // when
        when(goalController.getAll()).thenReturn(result);

        // then
        mockMvc.perform(get(DEFAULT_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(
                        document("전체 학습 목표 조회 성공 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(TAG)
                                        .description("전체 학습 목표 조회")
                                        .responseFields(
                                                fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("message").type(STRING).description("응답 메시지"),
                                                fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                                                fieldWithPath("data.goals[]").type(ARRAY).description("학습 목표 리스트"),
                                                fieldWithPath("data.goals[].goalType").type(STRING).description("학습 목표 ENUM 값"),
                                                fieldWithPath("data.goals[].name").type(STRING).description("학습 목표 이름")
                                        )
                                        .build()
                                )
                        ))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("학습 목표 조회 테스트")
    void success_get_goal_test() throws Exception {
        // given
        GoalType type = GoalType.APPLY;
        GoalResponseDTO response = new GoalResponseDTO(
                "현지 언어 체득",
                "주 5회 이상 오늘 하루를 돌아보는 일기 작성하기",
                "사전 없이 일기 완성\nsmeem 연속 일기 배지 획득");
        ResponseEntity<ApiResponse> result = ResponseEntity.ok(success("학습 목표 조회 성공", response));

        // when
        when(goalController.getByType(type)).thenReturn(result);

        // then
        mockMvc.perform(get(DEFAULT_URL + "/{type}", type)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(
                        document("학습 목표 조회 성공 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(TAG)
                                        .description("전체 학습 목표 조회")
                                        .pathParameters(
                                                parameterWithName("type").description("학습 목표 ENUM 값")
                                        )
                                        .responseFields(
                                                fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("message").type(STRING).description("응답 메시지"),
                                                fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                                                fieldWithPath("data.name").type(STRING).description("학습 목표 이름"),
                                                fieldWithPath("data.way").type(STRING).description("학습 목표 방식"),
                                                fieldWithPath("data.detail").type(STRING).description("학습 목표 내용")
                                        )
                                        .build()
                                )
                        ))
                .andExpect(status().isOk());
    }

    private List<GoalResponseVO> goals() {
        List<GoalResponseVO> goals = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            goals.add(new GoalResponseVO(GoalType.APPLY.name(), "현지 언어 체득" + (i + 1)));
        }
        return goals;
    }

}