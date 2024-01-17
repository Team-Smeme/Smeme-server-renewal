package com.smeme.server.controller;

import static com.smeme.server.util.ApiResponse.success;
import static com.smeme.server.util.Util.getMemberId;
import static com.smeme.server.util.Util.getURI;
import static com.smeme.server.util.message.ResponseMessage.*;

import java.security.Principal;

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

import com.smeme.server.dto.diary.CreatedDiaryResponseDTO;
import com.smeme.server.dto.diary.DiariesResponseDTO;
import com.smeme.server.dto.diary.DiaryRequestDTO;
import com.smeme.server.dto.diary.DiaryResponseDTO;
import com.smeme.server.service.DiaryService;
import com.smeme.server.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/diaries")
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<ApiResponse> save(Principal principal, @RequestBody DiaryRequestDTO request) {
        CreatedDiaryResponseDTO response = diaryService.save(getMemberId(principal), request);
        return ResponseEntity
                .created(getURI(response.diaryId()))
                .body(success(SUCCESS_CREATE_DIARY.getMessage(), response));
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<ApiResponse> getDetail(@PathVariable Long diaryId) {
        DiaryResponseDTO response = diaryService.getDetail(diaryId);
        return ResponseEntity.ok(success(SUCCESS_GET_DIARY.getMessage(), response));
    }

    @PatchMapping("/{diaryId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long diaryId, @RequestBody DiaryRequestDTO request) {
        diaryService.update(diaryId, request);
        return ResponseEntity.ok(success(SUCCESS_UPDATE_DAIRY.getMessage()));
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long diaryId) {
        diaryService.delete(diaryId);
        return ResponseEntity.ok(success(SUCCESS_DELETE_DIARY.getMessage()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getDiaries(
            Principal principal,
            @RequestParam(name = "start") String startDate,
            @RequestParam(name = "end") String endDate
    ) {
        DiariesResponseDTO response = diaryService.getDiaries(getMemberId(principal), startDate, endDate);
        return ResponseEntity.ok(success(SUCCESS_GET_DIARIES.getMessage(), response));
    }
}
