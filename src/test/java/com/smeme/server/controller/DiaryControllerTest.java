package com.smeme.server.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.smeme.server.dto.diary.CreatedDiaryResponseDTO;
import com.smeme.server.dto.diary.DiaryRequestDTO;
import com.smeme.server.util.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
        CreatedDiaryResponseDTO result = new CreatedDiaryResponseDTO(1L, badges());
        ResponseEntity<ApiResponse> response = ResponseEntity
                .created(URI.create("localhost:8080/api/v2/diaries/1"))
                .body(ApiResponse.success("일기 작성 성공", result));

        // when
        when(diaryController.save(principal, request)).thenReturn(response);

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
                                                fieldWithPath("data.badges[].imageUrl").type(STRING).description("뱃지 이미지 url")
                                        )
                                        .build()
                                )
                        ))
                .andExpect(status().isCreated());
    }

    private List<CreatedDiaryResponseDTO.BadgeDTO> badges() {
        List<CreatedDiaryResponseDTO.BadgeDTO> badges = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            badges.add(new CreatedDiaryResponseDTO.BadgeDTO("뱃지 이름" + (i + 1), "image-url" + (i + 1)));
        }
        return badges;
    }

}