package com.smeem.http.controller.docs;

import com.smeem.application.port.input.dto.request.diary.WriteDiaryRequest;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.application.port.input.dto.response.diary.RetrieveDiariesResponse;
import com.smeem.application.port.input.dto.response.diary.RetrieveDiaryResponse;
import com.smeem.application.port.input.dto.response.diary.WriteDiaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.Principal;

@Tag(name = "DiaryApi", description = "일기(Diary) 관련 Api 입니다.")
public interface DiaryApiDocs {

    @Operation(summary = "일기 작성 api", description = "일기를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "CREATED success",
                    content = @Content(schema = @Schema(implementation = WriteDiaryResponse.class)))
    })
    SmeemResponse<WriteDiaryResponse> writeDiary(
            @Parameter(hidden = true) Principal principal,
            @RequestBody(
                    description = "일기 작성 Request Body",
                    required = true,
                    content = @Content(schema = @Schema(implementation = WriteDiaryRequest.class))
            ) WriteDiaryRequest request
    );

    @Operation(summary = "일기 상세 조회 api", description = "일기 내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = RetrieveDiaryResponse.class)))
    })
    SmeemResponse<RetrieveDiaryResponse> retrieveDiary(
            @Parameter(
                    name = "diaryId",
                    description = "조회할 일기 id",
                    required = true,
                    in = ParameterIn.PATH
            ) long diaryId
    );

    @Operation(summary = "일기 수정 api", description = "일기를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success")
    })
    SmeemResponse<?> modifyDiary(
            @Parameter(hidden = true) Principal principal,
            @Parameter(
                    name = "diaryId",
                    description = "수정할 일기 id",
                    required = true,
                    in = ParameterIn.PATH
            ) long diaryId,
            @RequestBody(
                    description = "일기 수정 Request Body",
                    required = true,
                    content = @Content(schema = @Schema(implementation = WriteDiaryRequest.class))
            ) WriteDiaryRequest request
    );

    @Operation(summary = "일기 삭제 api", description = "일기를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "NO CONTENT success")
    })
    SmeemResponse<?> deleteDiary(
            @Parameter(
                    name = "diaryId",
                    description = "삭제할 일기 id",
                    required = true,
                    in = ParameterIn.PATH
            ) long diaryId
    );

    @Operation(summary = "일기 목록 조회 api", description = "기간 내 일기 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = RetrieveDiariesResponse.class)))
    })
    SmeemResponse<RetrieveDiariesResponse> retrieveDiariesByTerm(
            @Parameter(hidden = true) Principal principal,
            @Parameter(
                    name = "start",
                    description = "조회하려는 기간의 시작일",
                    required = true,
                    in = ParameterIn.QUERY
            ) String start,
            @Parameter(
                    name = "end",
                    description = "조회하려는 기간의 종료일",
                    required = true,
                    in = ParameterIn.QUERY
            ) String end
    );
}
