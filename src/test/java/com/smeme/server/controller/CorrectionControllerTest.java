package com.smeme.server.controller;


import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.smeme.server.fixture.correction.CorrectionFixture;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.security.Principal;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.smeme.server.util.ApiResponse.success;
import static com.smeme.server.util.message.ResponseMessage.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("CorrectionController 테스트")
@WebMvcTest(CorrectionController.class)
public class CorrectionControllerTest extends BaseControllerTest {

    @MockBean
    CorrectionController correctionController;

    @MockBean
    Principal principal;

    private final String DEFAULT_URL = "/api/v2/corrections";
    private final String TAG = "Correction";

    private static final Long DIARY_ID = 1L;
    private static final Long CORRECTION_ID = 1L;



    @DisplayName("첨삭 추가 API 성공")
    @Test
    void success_save() throws Exception {
        val resources = ResourceSnippetParameters.builder()
                .tag(TAG)
                .description("첨삭 추가")
                .requestFields(
                        fieldWithPath("sentence").type(STRING).description("첨삭 전 문장"),
                        fieldWithPath("content").type(STRING).description("첨삭 후 문장")
                )
                .responseHeaders(headerWithName("Location").description("생성된 첨삭 조회 URI"))
                .responseFields(
                        fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                        fieldWithPath("data.diaryId").type(NUMBER).description("일기 ID"),
                        fieldWithPath("data.badges").type(ARRAY).description("뱃지 목록"),
                        fieldWithPath("data.badges[].name").type(STRING).description("뱃지 이름"),
                        fieldWithPath("data.badges[].imageUrl").type(STRING).description("뱃지 이미지 URL")
                )
                .build();
        // when
        val result = ResponseEntity.
                created(URI.create(DEFAULT_URL +"/diary/" + DIARY_ID))
                .body(success(SUCCESS_CREATE_CORRECTION.getMessage(), CorrectionFixture.createCorrectionResponseDTO()));

        when(correctionController.save(principal, DIARY_ID, CorrectionFixture.createCorrectionRequestDTO()))
                .thenReturn(result);


        mockMvc.perform(post(DEFAULT_URL + "/diary/{diaryId}", DIARY_ID)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .principal(principal)
                        .content(objectMapper.writeValueAsString(CorrectionFixture.createCorrectionRequestDTO())))
                .andDo(
                        document("첨삭 추가 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(resources)
                        ))
                .andExpect(status().isCreated());
    }

    @DisplayName("첨삭 삭제 API 성공")
    @Test
    void success_delete() throws Exception {
        val resources = ResourceSnippetParameters.builder()
                .tag(TAG)
                .description("첨삭 삭제")
                .responseFields(
                        fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                        fieldWithPath("data").type(NULL).description("응답 데이터")
                )
                .build();
        // when
        val result = ResponseEntity.ok(success(SUCCESS_DELETE_CORRECTION.getMessage()));

        when(correctionController.delete(CORRECTION_ID))
                .thenReturn(result);


        mockMvc.perform(delete(DEFAULT_URL + "/{correctionId}", CORRECTION_ID)
                        .principal(principal)
                        .content(objectMapper.writeValueAsString(CorrectionFixture.createCorrectionRequestDTO())))
                .andDo(
                        document("첨삭 삭제 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(resources)
                        ))
                .andExpect(status().isOk());
    }

    @DisplayName("첨삭 수정 API 성공")
    @Test
    void success_update() throws Exception {
        val resources = ResourceSnippetParameters.builder()
                .tag(TAG)
                .description("첨삭 수정")
                .requestFields(
                        fieldWithPath("sentence").type(STRING).description("첨삭 전 문장"),
                        fieldWithPath("content").type(STRING).description("첨삭 후 문장")
                )
                .responseFields(
                        fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                        fieldWithPath("data").type(NULL).description("응답 데이터")
                )
                .build();
        // when
        val result = ResponseEntity.ok(success(SUCCESS_UPDATE_CORRECTION.getMessage()));
        when(correctionController.update(CORRECTION_ID, CorrectionFixture.createCorrectionRequestDTO()))
                .thenReturn(result);


        mockMvc.perform(patch(DEFAULT_URL + "/{correctionId}", CORRECTION_ID)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .principal(principal)
                        .content(objectMapper.writeValueAsString(CorrectionFixture.createCorrectionRequestDTO())))
                .andDo(
                        document("첨삭 수정 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(resources)
                        ))
                .andExpect(status().isOk());
    }

}
