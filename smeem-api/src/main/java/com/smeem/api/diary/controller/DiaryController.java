package com.smeem.api.diary.controller;

import java.security.Principal;

import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.BaseResponse;
import com.smeem.api.diary.controller.dto.request.DiaryCreateRequest;
import com.smeem.api.diary.controller.dto.request.DiaryModifyRequest;
import com.smeem.api.diary.controller.dto.response.DiaryCreateResponse;
import com.smeem.api.diary.controller.dto.response.DiaryGetResponse;
import com.smeem.api.diary.controller.dto.response.DiaryListGetResponse;
import com.smeem.api.diary.service.DiaryQueryService;
import com.smeem.api.diary.service.DiaryCommandService;
import com.smeem.api.diary.service.dto.request.*;
import com.smeem.common.util.Util;
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
public class DiaryController {

    private final DiaryCommandService diaryCommandService;
    private final DiaryQueryService diaryQueryService;

    @PostMapping
    public ResponseEntity<BaseResponse<?>> createDiary(Principal principal, @RequestBody DiaryCreateRequest request) {
        val memberId = Util.getMemberId(principal);
        val response = DiaryCreateResponse.from(
                diaryCommandService.createDiary(DiaryCreateServiceRequest.of(memberId, request)));
        val uri = Util.getURI("/{diaryId}", response.diaryId());
        return ApiResponseUtil.success(SUCCESS_CREATE_DIARY, uri, response);
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<BaseResponse<?>> getDiaryDetail(@PathVariable long diaryId) {
        val response = DiaryGetResponse.from(
                diaryQueryService.getDiaryDetail(DiaryGetServiceRequest.of(diaryId)));
        return ApiResponseUtil.success(SUCCESS_GET_DIARY, response);
    }

    @PatchMapping("/{diaryId}")
    public ResponseEntity<BaseResponse<?>> modifyDiary(@PathVariable long diaryId, @RequestBody DiaryModifyRequest request) {
        diaryCommandService.modifyDiary(DiaryModifyServiceRequest.of(diaryId, request));
        return ApiResponseUtil.success(SUCCESS_UPDATE_DAIRY);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<BaseResponse<?>> deleteDiary(@PathVariable long diaryId) {
        diaryCommandService.deleteDiary(DiaryDeleteServiceRequest.of(diaryId));
        return ApiResponseUtil.success(SUCCESS_DELETE_DIARY);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getDiaries(
            Principal principal,
            @RequestParam String start,
            @RequestParam String end
    ) {
        val memberId = Util.getMemberId(principal);
        val response = DiaryListGetResponse.from(
                diaryQueryService.getDiaries(DiaryListGetServiceRequest.of(memberId, start, end)));
        return ApiResponseUtil.success(SUCCESS_GET_DIARIES, response);
    }
}
