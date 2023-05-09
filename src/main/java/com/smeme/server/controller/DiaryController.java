package com.smeme.server.controller;

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

import com.smeme.server.dto.diary.DiariesResponseDTO;
import com.smeme.server.dto.diary.DiaryRequestDTO;
import com.smeme.server.dto.diary.DiaryResponseDTO;
import com.smeme.server.service.DiaryService;
import com.smeme.server.util.ApiResponse;
import com.smeme.server.util.Util;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/diaries")
public class DiaryController {

	private final DiaryService diaryService;

	@PostMapping
	public ResponseEntity<ApiResponse> createDiary(Principal principal, @RequestBody DiaryRequestDTO requestDTO) {
		Long memberId = Util.getMemberId(principal);
		Long diaryId = diaryService.createDiary(memberId, requestDTO);
		return ResponseEntity
			.created(Util.getURI(diaryId))
			.body(ApiResponse.success(SUCCESS_CREATE_DIARY.getMessage()));
	}

	@GetMapping("/{diaryId}")
	public ResponseEntity<ApiResponse> getDiaryDetail(@PathVariable Long diaryId) {
		DiaryResponseDTO response = diaryService.getDiaryDetail(diaryId);
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_GET_DIARY.getMessage(), response));
	}

	@PatchMapping("/{diaryId}")
	public ResponseEntity<ApiResponse> updateDiary(
		@PathVariable Long diaryId, @RequestBody DiaryRequestDTO requestDTO) {
		diaryService.updateDiary(diaryId, requestDTO);
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_UPDATE_DAIRY.getMessage()));
	}

	@DeleteMapping("/{diaryId}")
	public ResponseEntity<ApiResponse> deleteDiary(@PathVariable Long diaryId) {
		diaryService.deleteDiary(diaryId);
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_DELETE_DIARY.getMessage()));
	}

	@GetMapping
	public ResponseEntity<ApiResponse> getDiaries(Principal principal,
		@RequestParam(name = "start") String startDate, @RequestParam(name = "end") String endDate) {
		DiariesResponseDTO response = diaryService.getDiaries(Util.getMemberId(principal), startDate, endDate);
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_GET_DIARIES.getMessage(), response));
	}
}
