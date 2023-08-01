package com.smeme.server.controller;

import static com.smeme.server.util.message.ResponseMessage.*;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.dto.correction.CorrectionRequestDTO;
import com.smeme.server.dto.correction.CorrectionResponseDTO;
import com.smeme.server.dto.diary.DiariesResponseDTO;
import com.smeme.server.service.CorrectionService;
import com.smeme.server.util.ApiResponse;
import com.smeme.server.util.Util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Correction", description = "첨삭 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/corrections")
public class CorrectionController {

	private final CorrectionService correctionService;

	@Operation(summary = "일기 첨삭", description = "일기 첨삭을 생성합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "201",
			description = "일기 첨삭 성공")
	})
	@PostMapping("/diary/{diaryId}")
	public ResponseEntity<ApiResponse> createCorrection(
		@Parameter(hidden = true) Principal principal,
		@Parameter(description = "일기 id") @PathVariable Long diaryId,
		@RequestBody CorrectionRequestDTO requestDTO
	) {
		Long memberId = Util.getMemberId(principal);
		CorrectionResponseDTO response = correctionService.createCorrection(memberId, diaryId, requestDTO);
		return ResponseEntity
			.created(Util.getURI(diaryId))
			.body(ApiResponse.success(SUCCESS_CREATE_CORRECTION.getMessage(), response));
	}

	@Operation(summary = "첨삭 삭제", description = "첨삭을 삭제합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "200",
			description = "첨삭 삭제 성공")
	})
	@DeleteMapping("/{correctionId}")
	public ResponseEntity<ApiResponse> deleteCorrection(
		@Parameter(description = "첨삭 id") @PathVariable Long correctionId
	) {
		correctionService.deleteCorrection(correctionId);
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_DELETE_CORRECTION.getMessage()));
	}

	@Operation(summary = "첨삭 수정", description = "첨삭을 수정합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "200",
			description = "첨삭 수정 성공")
	})
	@PatchMapping("/{correctionId}")
	public ResponseEntity<ApiResponse> updateCorrection(
		@Parameter(description = "첨삭 id") @PathVariable Long correctionId,
		@RequestBody CorrectionRequestDTO requestDTO
	) {
		correctionService.updateCorrection(correctionId, requestDTO);
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_UPDATE_CORRECTION.getMessage()));
	}
}
