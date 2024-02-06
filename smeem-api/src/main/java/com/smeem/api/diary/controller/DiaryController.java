package com.smeem.api.diary.controller;


import java.security.Principal;

import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.BaseResponse;
import com.smeem.api.diary.controller.dto.request.DiaryRequestDTO;
import com.smeem.api.diary.controller.dto.response.CreatedDiaryResponseDTO;
import com.smeem.api.diary.controller.dto.response.DiariesResponseDTO;
import com.smeem.api.diary.controller.dto.response.DiaryResponseDTO;
import com.smeem.api.diary.service.DiaryService;
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
    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<BaseResponse<?>> save(Principal principal, @RequestBody DiaryRequestDTO request) {
        CreatedDiaryResponseDTO response = diaryService.save(Util.getMemberId(principal), request);
        val uri = Util.getURI("/{diaryId}", response.diaryId());
        return ApiResponseUtil.success(SUCCESS_CREATE_DIARY, uri, response);
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<BaseResponse<?>> getDetail(@PathVariable Long diaryId) {
        DiaryResponseDTO response = diaryService.getDetail(diaryId);
        return ApiResponseUtil.success(SUCCESS_GET_DIARY, response);
    }

    @PatchMapping("/{diaryId}")
    public ResponseEntity<BaseResponse<?>> update(@PathVariable Long diaryId, @RequestBody DiaryRequestDTO request) {
        diaryService.update(diaryId, request);
        return ApiResponseUtil.success(SUCCESS_UPDATE_DAIRY);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<BaseResponse<?>> delete(@PathVariable Long diaryId) {
        diaryService.delete(diaryId);
        return ApiResponseUtil.success(SUCCESS_DELETE_DIARY);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getDiaries(
            Principal principal,
            @RequestParam(name = "start") String startDate,
            @RequestParam(name = "end") String endDate
    ) {
        DiariesResponseDTO response = diaryService.getDiaries(Util.getMemberId(principal), startDate, endDate);
        return ApiResponseUtil.success(SUCCESS_GET_DIARIES, response);
    }
}
