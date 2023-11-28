package com.smeme.server.controller;


import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.smeme.server.fixture.member.MemberFixture;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.smeme.server.util.message.ResponseMessage.SUCCESS_UPDATE_USER_PLAN;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static com.smeme.server.util.ApiResponse.success;
import static com.smeme.server.util.message.ResponseMessage.SUCCESS_UPDATE_USER_PUSH;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Member Controller Test")
@WebMvcTest(MemberController.class)
public class MemberControllerTest extends BaseControllerTest {

    private static final String DEFAULT_URL = "/api/v2/members";
    private static final String TAG = "Member";

    @MockBean
    MemberController memberController;

    @MockBean
    Principal principal;

    @Test
    @DisplayName("유저 프로필 업데이트 성공")
    void success_updateProfile() throws Exception {
        val resources = ResourceSnippetParameters.builder()
                .tag(TAG)
                .description("유저 프로필 업데이트")
                .responseFields(
                        fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                        fieldWithPath("data.username").type(NULL).description("유저 닉네임"),
                        fieldWithPath("data.target").type(STRING).description("학습목표"),
                        fieldWithPath("data.way").type(STRING).description("목표 달성 방법"),
                        fieldWithPath("data.detail").type(STRING).description("목표 달성 방법"),
                        fieldWithPath("data.targetLang").type(STRING).description("목표 언어"),
                        fieldWithPath("data.hasPushAlarm").type(BOOLEAN).description("푸시 알림 여부"),
                        fieldWithPath("data.trainingTime").type(OBJECT).description("학습 시간"),
                        fieldWithPath("data.trainingTime.day").type(STRING).description("학습 요일"),
                        fieldWithPath("data.trainingTime.hour").type(NUMBER).description("학습 시간"),
                        fieldWithPath("data.trainingTime.minute").type(NUMBER).description("학습 분"),
                        fieldWithPath("data.badge").type(OBJECT).description("뱃지"),
                        fieldWithPath("data.badge.id").type(NUMBER).description("뱃지 아이디"),
                        fieldWithPath("data.badge.name").type(STRING).description("뱃지 이름"),
                        fieldWithPath("data.badge.type").type(STRING).description("뱃지 설명"),
                        fieldWithPath("data.badge.imageUrl").type(STRING).description("뱃지 이미지")
                )
                .build();

            val response = ResponseEntity.ok(success("유저 프로필 업데이트 성공", MemberFixture.createMemberGetResponseDTO()));

            when(memberController.updateProfile(principal, MemberFixture.createMemberUpdateRequestDTO()))
                    .thenReturn(response);

            mockMvc.perform(patch(DEFAULT_URL)
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .principal(principal)
                    .content(objectMapper.writeValueAsString(MemberFixture.createMemberUpdateRequestDTO()))
            ).andDo(
                    document("유저 프로필 업데이트 성공 Example",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            resource(resources)
                    ))
            .andExpect(status().isOk());
    }

    @DisplayName("사용자 정보 조회")
    @Test
    void success_getProfile() throws Exception {
        val resources = ResourceSnippetParameters.builder()
                .tag(TAG)
                .description("사용자 정보 조회")
                .responseFields(
                        fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                        fieldWithPath("data.username").type(NULL).description("유저 닉네임"),
                        fieldWithPath("data.target").type(STRING).description("학습목표"),
                        fieldWithPath("data.way").type(STRING).description("목표 달성 방법"),
                        fieldWithPath("data.detail").type(STRING).description("목표 달성 방법"),
                        fieldWithPath("data.targetLang").type(STRING).description("목표 언어"),
                        fieldWithPath("data.hasPushAlarm").type(BOOLEAN).description("푸시 알림 여부"),
                        fieldWithPath("data.trainingTime").type(OBJECT).description("학습 시간"),
                        fieldWithPath("data.trainingTime.day").type(STRING).description("학습 요일"),
                        fieldWithPath("data.trainingTime.hour").type(NUMBER).description("학습 시간"),
                        fieldWithPath("data.trainingTime.minute").type(NUMBER).description("학습 분"),
                        fieldWithPath("data.badge").type(OBJECT).description("뱃지"),
                        fieldWithPath("data.badge.id").type(NUMBER).description("뱃지 아이디"),
                        fieldWithPath("data.badge.name").type(STRING).description("뱃지 이름"),
                        fieldWithPath("data.badge.type").type(STRING).description("뱃지 설명"),
                        fieldWithPath("data.badge.imageUrl").type(STRING).description("뱃지 이미지")
                )
                .build();
        val result = ResponseEntity.ok(success("사용자 정보 조회 성공", MemberFixture.createMemberGetResponseDTO()));

        when(memberController.getProfile(principal))
                .thenReturn(result);

        mockMvc.perform(get(DEFAULT_URL + "/me")
                .contentType(APPLICATION_JSON)
                .principal(principal))
                .andDo(
                        document("사용자 정보 조회 성공 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(resources)
                        ))
                .andExpect(status().isOk());
    }

    @DisplayName("Member 학습계획 수정 API 성공 ")
    @Test
    void success_updateUserPlan() throws Exception {
        val resources = ResourceSnippetParameters.builder()
                .tag(TAG)
                .description("사용자 수정 API")
                .responseFields(
                        fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                        fieldWithPath("data").type(NULL).description("응답 데이터")
                )
                .build();
        val result = ResponseEntity.ok(success(SUCCESS_UPDATE_USER_PLAN.getMessage()));

        when(memberController.updateUserPlan(principal, MemberFixture.createMemberPlanUpdateRequestDTO()))
                .thenReturn(result);

        mockMvc.perform(patch(DEFAULT_URL+"/plan")
                .contentType(APPLICATION_JSON)
                .principal(principal)
                .content(objectMapper.writeValueAsString(MemberFixture.createMemberPlanUpdateRequestDTO())))
                .andDo(
                        document("사용자 정보 수정 성공 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(resources)
                        ))
                .andExpect(status().isOk());
    }

    @DisplayName("유저 이름 중복체크 API 성공")
    @Test
    void success_checkDuplicatedName() throws Exception {
        // given
        val resources = ResourceSnippetParameters.builder()
                .tag(TAG)
                .description("닉네임 중복 체크")
                .responseFields(
                        fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                        fieldWithPath("data").type(NULL).description("응답 데이터")
                )
                .build();
        // when
        when(memberController.checkDuplicatedName("test"))
                .thenReturn(ResponseEntity.ok(success("닉네임 중복 체크 성공")));

        // then
        mockMvc.perform(get(DEFAULT_URL + "/nickname/check")
                        .contentType(APPLICATION_JSON)
                        .principal(principal)
                        .param("name", "test"))
                .andDo(
                        document("닉네임 중복 체크 성공 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(resources)
                        ))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("푸시알림 동의 여부 수정 메소드 테스트")
    void success_updateUserPush() throws Exception {
        // given
        val resources = ResourceSnippetParameters.builder()
                        .tag(TAG)
                        .description("푸시 알림 동의여부 수정")
                        .requestFields(fieldWithPath("hasAlarm").type(BOOLEAN).description("푸시 알림 동의여부"))
                        .responseFields(
                                fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                                fieldWithPath("message").type(STRING).description("응답 메시지"),
                                fieldWithPath("data").type(NULL).description("응답 데이터")
                        )
                        .build();
        // when
        when(memberController.updateUserPush(principal, MemberFixture.createMemberPushUpdateRequestDTO()))
                .thenReturn(ResponseEntity.ok(success(SUCCESS_UPDATE_USER_PUSH.getMessage())));


        mockMvc.perform(patch(DEFAULT_URL + "/push")
                        .contentType(APPLICATION_JSON)
                        .principal(principal)
                        .content(objectMapper.writeValueAsString(MemberFixture.createMemberPushUpdateRequestDTO())))
                .andDo(
                        document("푸시 알림 동의여부 성공 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(resources)
                        ))
                .andExpect(status().isOk());
    }
}