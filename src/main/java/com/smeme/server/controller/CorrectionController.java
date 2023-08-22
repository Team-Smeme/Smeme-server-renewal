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
import com.smeme.server.service.CorrectionService;
import com.smeme.server.util.ApiResponse;
import com.smeme.server.util.Util;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/corrections")
public class CorrectionController {

	private final CorrectionService correctionService;

	@PostMapping("/diary/{diaryId}")
	public ResponseEntity<ApiResponse> createCorrection(
		Principal principal, @PathVariable Long diaryId, @RequestBody CorrectionRequestDTO requestDTO
	) {
		Long memberId = Util.getMemberId(principal);
		CorrectionResponseDTO response = correctionService.createCorrection(memberId, diaryId, requestDTO);
		return ResponseEntity
			.created(Util.getURI(diaryId))
			.body(ApiResponse.success(SUCCESS_CREATE_CORRECTION.getMessage(), response));
	}

	@DeleteMapping("/{correctionId}")
	public ResponseEntity<ApiResponse> deleteCorrection(@PathVariable Long correctionId) {
		correctionService.deleteCorrection(correctionId);
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_DELETE_CORRECTION.getMessage()));
	}

	@PatchMapping("/{correctionId}")
	public ResponseEntity<ApiResponse> updateCorrection(
		@PathVariable Long correctionId, @RequestBody CorrectionRequestDTO requestDTO
	) {
		correctionService.updateCorrection(correctionId, requestDTO);
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_UPDATE_CORRECTION.getMessage()));
	}
}
