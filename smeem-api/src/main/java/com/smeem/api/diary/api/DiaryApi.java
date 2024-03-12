package com.smeem.api.diary.api;

import com.smeem.api.common.BaseResponse;
import com.smeem.api.diary.api.dto.request.DiaryCreateRequest;
import com.smeem.api.diary.api.dto.request.DiaryModifyRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

public interface DiaryApi {

    @Operation(
            summary = "일기 생성",
            responses = {
                    @ApiResponse(responseCode = "201", description = "성공"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<BaseResponse<?>> createDiary(Principal principal, @RequestBody DiaryCreateRequest request);

    @Operation(
            summary = "일기 상세 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<BaseResponse<?>> getDiaryDetail(@PathVariable long diaryId);

    @Operation(
            summary = "일기 수정",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<BaseResponse<?>> modifyDiary(@PathVariable long diaryId, @RequestBody DiaryModifyRequest request);

    @Operation(
            summary = "일기 삭제",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<BaseResponse<?>> deleteDiary(@PathVariable long diaryId);

    @Operation(
            summary = "기간 내 일기 목록 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<BaseResponse<?>> getDiaries(
            Principal principal,
            @RequestParam String start,
            @RequestParam String end
    );
}
