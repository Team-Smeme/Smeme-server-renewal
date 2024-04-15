package com.smeem.api.diary.api;

import com.smeem.api.common.FailureResponse;
import com.smeem.api.common.SuccessResponse;
import com.smeem.api.diary.api.dto.request.DiaryCreateRequest;
import com.smeem.api.diary.api.dto.request.DiaryModifyRequest;
import com.smeem.api.diary.api.dto.response.DiaryCreateResponse;
import com.smeem.api.diary.api.dto.response.DiaryGetResponse;
import com.smeem.api.diary.api.dto.response.DiaryListGetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "[Diary] 일기 관련 API (V2)")
public interface DiaryApi {

    @Operation(summary = "일기 생성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(
                    responseCode = "4xx",
                    description = "유효하지 않은 요청",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "유효하지 않은 토큰",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            )
    })
    ResponseEntity<SuccessResponse<DiaryCreateResponse>> createDiary(
            @Parameter(hidden = true) Principal principal,
            @RequestBody DiaryCreateRequest request
    );

    @Operation(summary = "일기 상세 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(
                    responseCode = "4xx",
                    description = "유효하지 않은 요청",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "유효하지 않은 토큰",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            )
    })
    ResponseEntity<SuccessResponse<DiaryGetResponse>> getDiaryDetail(@PathVariable long diaryId);

    @Operation(summary = "일기 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(
                    responseCode = "4xx",
                    description = "유효하지 않은 요청",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "유효하지 않은 토큰",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            )
    })
    ResponseEntity<SuccessResponse<?>> modifyDiary(@PathVariable long diaryId, @RequestBody DiaryModifyRequest request);

    @Operation(summary = "일기 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(
                    responseCode = "4xx",
                    description = "유효하지 않은 요청",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "유효하지 않은 토큰",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            )
    })
    ResponseEntity<SuccessResponse<?>> deleteDiary(@PathVariable long diaryId);

    @Operation(summary = "기간 내 일기 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(
                    responseCode = "4xx",
                    description = "유효하지 않은 요청",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "유효하지 않은 토큰",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = FailureResponse.class))
            )
    })
    ResponseEntity<SuccessResponse<DiaryListGetResponse>> getDiaries(
            @Parameter(hidden = true) Principal principal,
            @RequestParam String start,
            @RequestParam String end
    );
}
