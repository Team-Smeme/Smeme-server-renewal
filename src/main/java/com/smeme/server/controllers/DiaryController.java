package com.smeme.server.controllers;

import com.smeme.server.dtos.diary.*;
import com.smeme.server.services.DiaryService;
import com.smeme.server.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<ApiResponse> findPublicDiaries(
            @RequestParam(name = "category", required = false) String category,
            @RequestBody DiaryPublicFindRequestDto diaryRequestDto
    ) {
        List<DiaryPublicFindResponseDto> diariesResponseDto
                = diaryService.findPublicDiaries(category, diaryRequestDto.userId());

        ApiResponse apiResponse = ApiResponse.of(
                HttpStatus.OK.value(), true, "게시판 일기 조회 성공", diariesResponseDto);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<ApiResponse> findPublicDiary(
            @PathVariable("diaryId") String diaryId, @RequestBody DiaryPublicFindRequestDto diaryRequestDto) {

        DiaryPublicDetailFindResponseDto diary
                = diaryService.findPublicDiaryById(Long.parseLong(diaryId), diaryRequestDto.userId());

        ApiResponse apiResponse = ApiResponse.of(
                HttpStatus.OK.value(), true, "게시판 일기 상세 조회 성공", diary);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity<ApiResponse> likeDiary(@RequestBody DiaryLikeRequestDto requestDto) {
        DiaryLikeResponseDto response = diaryService.likeDiary(requestDto.diaryId(), requestDto.userId());

        ApiResponse apiResponse;

        if (response.hasLike()) {
            apiResponse = ApiResponse.of(HttpStatus.OK.value(), true, "추천 성공", response);
        } else {
            apiResponse = ApiResponse.of(HttpStatus.OK.value(), true, "추천 해제 성공", response);
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
