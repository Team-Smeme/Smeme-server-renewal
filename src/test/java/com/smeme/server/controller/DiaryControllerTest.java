package com.smeme.server.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.smeme.server.dto.badge.AcquiredBadgeResponseDTO;
import com.smeme.server.dto.diary.CreatedDiaryResponseDTO;
import com.smeme.server.dto.diary.DiariesResponseDTO;
import com.smeme.server.dto.diary.DiariesResponseDTO.DiaryDTO;
import com.smeme.server.dto.diary.DiaryRequestDTO;
import com.smeme.server.dto.diary.DiaryResponseDTO;
import com.smeme.server.model.badge.BadgeType;
import com.smeme.server.util.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.smeme.server.util.ApiResponse.success;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("DiaryController 테스트")
@WebMvcTest(DiaryController.class)
class DiaryControllerTest extends BaseControllerTest {
    @MockBean
    DiaryController diaryController;
    @MockBean
    Principal principal;

    private final String DEFAULT_URL = "/api/v2/diaries";
    private final String TAG = "Diary";

    @Test
    @DisplayName("일기 작성 테스트")
    void success_create_diary() throws Exception {
        // given
        DiaryRequestDTO request = new DiaryRequestDTO("Hello SMEEM!", 1L);
        CreatedDiaryResponseDTO response = new CreatedDiaryResponseDTO(1L, acquiredBadges());
        ResponseEntity<ApiResponse> result = ResponseEntity
                .created(URI.create("localhost:8080/api/v2/diaries/1"))
                .body(success("일기 작성 성공", response));

        // when
        when(diaryController.save(principal, request)).thenReturn(result);

        // then
        mockMvc.perform(post(DEFAULT_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .principal(principal)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(
                        document("일기 작성 성공 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(TAG)
                                        .description("일기 작성")
                                        .requestFields(
                                                fieldWithPath("content").type(STRING).description("일기 내용"),
                                                fieldWithPath("topicId").type(NUMBER).description("랜덤 주제 id(8~54) or null")
                                        )
                                        .responseHeaders(
                                                headerWithName("Location").description("생성된 일기 조회 URI")
                                        )
                                        .responseFields(
                                                fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("message").type(STRING).description("응답 메시지"),
                                                fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                                                fieldWithPath("data.diaryId").type(NUMBER).description("생성한 일기 id"),
                                                fieldWithPath("data.badges[]").type(ARRAY).description("획득한 뱃지 리스트"),
                                                fieldWithPath("data.badges[].name").type(STRING).description("뱃지 이름"),
                                                fieldWithPath("data.badges[].imageUrl").type(STRING).description("뱃지 이미지 url"),
                                                fieldWithPath("data.badges[].type").type(STRING).description("뱃지 타입")
                                        )
                                        .build()
                                )
                        ))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("일기 조회 테스트")
    void success_get_diary() throws Exception {
        // given
        DiaryResponseDTO response = new DiaryResponseDTO(1L,
                "가보고 싶은 해외 여행 지가 있다면 소개해 주세요!",
                "I want to go Bla Bla ...",
                "2023-08-19 14:00",
                "스미무");
        ResponseEntity<ApiResponse> result = ResponseEntity.ok(success("일기 조회 성공", response));

        // when
        when(diaryController.getDetail(anyLong())).thenReturn(result);

        // then
        mockMvc.perform(get(DEFAULT_URL + "/{diaryId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                )
                .andDo(
                        document("일기 조회 성공 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(TAG)
                                        .description("일기 조회")
                                        .pathParameters(
                                                parameterWithName("diaryId").description("일기 id")
                                        )
                                        .responseFields(
                                                fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("message").type(STRING).description("응답 메시지"),
                                                fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                                                fieldWithPath("data.diaryId").type(NUMBER).description("일기 id"),
                                                fieldWithPath("data.topic").type(STRING).description("랜덤 주제"),
                                                fieldWithPath("data.content").type(STRING).description("일기 내용"),
                                                fieldWithPath("data.createdAt").type(STRING).description("작성 날짜"),
                                                fieldWithPath("data.username").type(STRING).description("작성자 이름")
                                        )
                                        .build()
                                )
                        ))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("일기 수정 테스트")
    void success_update_diary() throws Exception {
        // given
        Long diaryId = 1L;
        DiaryRequestDTO request = new DiaryRequestDTO("Hello SMEEM!!", 1L);
        ResponseEntity<ApiResponse> result = ResponseEntity.ok(success("일기 수정 성공"));

        // when
        when(diaryController.update(diaryId, request)).thenReturn(result);

        // then
        mockMvc.perform(patch(DEFAULT_URL + "/{diaryId}", diaryId)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(
                        document("일기 수정 성공 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(TAG)
                                        .description("일기 수정")
                                        .pathParameters(
                                                parameterWithName("diaryId").description("일기 id")
                                        )
                                        .requestFields(
                                                fieldWithPath("content").type(STRING).description("수정할 일기 내용"),
                                                fieldWithPath("topicId").type(NUMBER).description("랜덤 주제 id")
                                        )
                                        .responseFields(
                                                fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("message").type(STRING).description("응답 메시지"),
                                                fieldWithPath("data").type(NULL).description("응답 데이터")
                                        )
                                        .build()
                                )
                        ))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("일기 삭제 테스트")
    void success_delete_diary() throws Exception {
        // given
        Long diaryId = 1L;
        ResponseEntity<ApiResponse> result = ResponseEntity.ok(success("일기 삭제 성공"));

        // when
        when(diaryController.delete(diaryId)).thenReturn(result);

        // then
        mockMvc.perform(delete(DEFAULT_URL + "/{diaryId}", diaryId)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(
                        document("일기 삭제 성공 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(TAG)
                                        .description("일기 삭제")
                                        .pathParameters(
                                                parameterWithName("diaryId").description("일기 id")
                                        )
                                        .responseFields(
                                                fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("message").type(STRING).description("응답 메시지"),
                                                fieldWithPath("data").type(NULL).description("응답 데이터")
                                        )
                                        .build()
                                )
                        ))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("일기 리스트 조회 테스트")
    void success_get_diaries() throws Exception {
        // given
        DiariesResponseDTO response = new DiariesResponseDTO(diaries(), true);
        ResponseEntity<ApiResponse> result = ResponseEntity.ok(success("일기 리스트 조회 성공", response));

        MultiValueMap<String, String> queries = new LinkedMultiValueMap<>();
        String startDate = "2023-08-18 00:00";
        String endDate = "2023-09-18 00:00";
        queries.add("start", startDate);
        queries.add("end", endDate);

        // when
        when(diaryController.getDiaries(principal, startDate, endDate)).thenReturn(result);

        // then
        mockMvc.perform(get(DEFAULT_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .principal(principal)
                        .params(queries))
                .andDo(
                        document("일기 리스트 조회 성공 Example",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag(TAG)
                                        .description("일기 리스트 조회")
                                        .queryParameters(
                                                parameterWithName("start").description("범위 시작 날짜(yyyy-MM-dd HH:mm)"),
                                                parameterWithName("end").description("범위 끝 날짜(yyyy-MM-dd HH:mm)")
                                        )
                                        .responseFields(
                                                fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
                                                fieldWithPath("message").type(STRING).description("응답 메시지"),
                                                fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                                                fieldWithPath("data.diaries[]").type(ARRAY).description("일기 정보 리스트"),
                                                fieldWithPath("data.diaries[].diaryId").type(NUMBER).description("일기 id"),
                                                fieldWithPath("data.diaries[].content").type(STRING).description("일기 내용"),
                                                fieldWithPath("data.diaries[].createdAt").type(STRING).description("일기 작성 일자"),
                                                fieldWithPath("data.has30Past").type(BOOLEAN).description("30일 전 일기 존재 여부")
                                        )
                                        .build()
                                )
                        ))
                .andExpect(status().isOk());
    }

    private List<AcquiredBadgeResponseDTO> acquiredBadges() {
        return Stream.iterate(1, i -> i + 1).limit(5)
                .map(i -> acquiredBadge()).toList();
    }

    private AcquiredBadgeResponseDTO acquiredBadge() {
        return new AcquiredBadgeResponseDTO("뱃지 이름", "badge-image-url", BadgeType.EVENT);
    }

    private List<DiaryDTO> diaries() {
        List<DiaryDTO> diaries = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            diaries.add(new DiaryDTO((long) (i + 1), "Hello SMEEM" + (i + 1), "2023-08-2" + i + " 14:00"));
        }
        return diaries;
    }
}