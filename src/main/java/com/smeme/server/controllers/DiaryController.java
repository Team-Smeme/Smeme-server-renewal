package com.smeme.server.controllers;

import com.smeme.server.dtos.diary.DiaryCreateRequestDto;
import com.smeme.server.dtos.diary.DiaryCreateResponseDto;
import com.smeme.server.services.DiaryService;
import com.smeme.server.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<ApiResponse> createDiary(@RequestBody DiaryCreateRequestDto diaryRequestDto) {
        DiaryCreateResponseDto diaryResponseDto = diaryService.createDiary(diaryRequestDto);

        ApiResponse apiResponse = ApiResponse.of(
                HttpStatus.CREATED.value(), true, "일기 작성 성공", diaryResponseDto);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
