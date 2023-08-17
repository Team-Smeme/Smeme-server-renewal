package com.smeme.server.controller;

import static com.smeme.server.util.message.ResponseMessage.*;

import java.security.Principal;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
import com.smeme.server.util.Util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Diary", description = "일기 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/diaries")
@SecurityRequirement(name = "Authorization")
public class DiaryController {

	private final DiaryService diaryService;

	@PostMapping
	public ResponseEntity<ApiResponse> createDiary(Principal principal, @RequestBody DiaryRequestDTO requestDTO) {
		Long memberId = Util.getMemberId(principal);
		CreatedDiaryResponseDTO response = diaryService.createDiary(memberId, requestDTO);
		return ResponseEntity
			.created(Util.getURI(response.diaryId()))
			.body(ApiResponse.success(SUCCESS_CREATE_DIARY.getMessage(), response));
	}

	@GetMapping("/{diaryId}")
	public ResponseEntity<ApiResponse> getDiaryDetail(@PathVariable Long diaryId) {
		DiaryResponseDTO response = diaryService.getDiaryDetail(diaryId);
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_GET_DIARY.getMessage(), response));
	}

	@Operation(summary = "일기 수정", description = "일기를 수정합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "200",
			description = "일기 수정 성공")
	})
	@PatchMapping("/{diaryId}")
	public ResponseEntity<ApiResponse> updateDiary(
		@Parameter(name = "일기 id") @PathVariable Long diaryId, @RequestBody DiaryRequestDTO requestDTO) {
		diaryService.updateDiary(diaryId, requestDTO);
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_UPDATE_DAIRY.getMessage()));
	}

	@Operation(summary = "일기 삭제", description = "일기를 삭제합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "200",
			description = "일기 삭제 성공")
	})
	@DeleteMapping("/{diaryId}")
	public ResponseEntity<ApiResponse> deleteDiary(@Parameter(name = "일기 id") @PathVariable Long diaryId) {
		diaryService.deleteDiary(diaryId);
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_DELETE_DIARY.getMessage()));
	}

	@Operation(summary = "일기 리스트 조회", description = "일기 리스트를 조회합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "200",
			description = "일기 리스트 조회 성공",
			content = @Content(schema = @Schema(implementation = DiariesResponseDTO.class)))
	})
	@GetMapping
	public ResponseEntity<ApiResponse> getDiaries(
		@Parameter(hidden = true) Principal principal,
		@Parameter(name = "범위 시작 날짜(yyyy-MM-dd HH:mm)") @RequestParam(name = "start") String startDate,
		@Parameter(name = "범위 끝 날짜(yyyy-MM-dd HH:mm)") @RequestParam(name = "end") String endDate
	) {
		DiariesResponseDTO response = diaryService.getDiaries(Util.getMemberId(principal), startDate, endDate);
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_GET_DIARIES.getMessage(), response));
	}
}
