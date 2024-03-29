package com.smeem.api.diary.api;

import java.security.Principal;

import com.smeem.api.support.ApiResponseGenerator;
import com.smeem.api.common.SuccessResponse;
import com.smeem.api.diary.api.dto.request.DiaryCreateRequest;
import com.smeem.api.diary.api.dto.request.DiaryModifyRequest;
import com.smeem.api.diary.api.dto.response.DiaryCreateResponse;
import com.smeem.api.diary.api.dto.response.DiaryGetResponse;
import com.smeem.api.diary.api.dto.response.DiaryListGetResponse;
import com.smeem.api.diary.service.DiaryQueryService;
import com.smeem.api.diary.service.DiaryCommandService;
import com.smeem.api.diary.service.dto.request.*;
import com.smeem.api.support.PrincipalConverter;
import com.smeem.api.support.UriConverter;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

import static com.smeem.common.code.success.DiarySuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/diaries")
public class DiaryApiController implements DiaryApi {

    private final DiaryCommandService diaryCommandService;
    private final DiaryQueryService diaryQueryService;

    @PostMapping
    public ResponseEntity<SuccessResponse<DiaryCreateResponse>> createDiary(Principal principal, @RequestBody DiaryCreateRequest request) {
        val memberId = PrincipalConverter.getMemberId(principal);
        val response = DiaryCreateResponse.from(
                diaryCommandService.createDiary(DiaryCreateServiceRequest.of(memberId, request)));
        val uri = UriConverter.getURI("/{diaryId}", response.diaryId());
        return ApiResponseGenerator.success(SUCCESS_CREATE_DIARY, uri, response);
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<SuccessResponse<DiaryGetResponse>> getDiaryDetail(@PathVariable long diaryId) {
        val response = DiaryGetResponse.from(
                diaryQueryService.getDiaryDetail(DiaryGetServiceRequest.of(diaryId)));
        return ApiResponseGenerator.success(SUCCESS_GET_DIARY, response);
    }

    @PatchMapping("/{diaryId}")
    public ResponseEntity<SuccessResponse<?>> modifyDiary(@PathVariable long diaryId, @RequestBody DiaryModifyRequest request) {
        diaryCommandService.modifyDiary(DiaryModifyServiceRequest.of(diaryId, request));
        return ApiResponseGenerator.success(SUCCESS_UPDATE_DAIRY);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<SuccessResponse<?>> deleteDiary(@PathVariable long diaryId) {
        diaryCommandService.deleteDiary(DiaryDeleteServiceRequest.of(diaryId));
        return ApiResponseGenerator.success(SUCCESS_DELETE_DIARY);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<DiaryListGetResponse>> getDiaries(
            Principal principal,
            @RequestParam String start,
            @RequestParam String end
    ) {
        val memberId = PrincipalConverter.getMemberId(principal);
        val response = DiaryListGetResponse.from(
                diaryQueryService.getDiaries(DiaryListGetServiceRequest.of(memberId, start, end)));
        return ApiResponseGenerator.success(SUCCESS_GET_DIARIES, response);
    }
}
