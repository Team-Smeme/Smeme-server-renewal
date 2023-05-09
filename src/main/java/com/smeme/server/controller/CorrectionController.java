package com.smeme.server.controller;

import static com.smeme.server.util.message.ResponseMessage.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.dto.correction.CorrectionRequestDTO;
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
		@PathVariable Long diaryId, @RequestBody CorrectionRequestDTO requestDTO) {
		correctionService.createCorrection(diaryId, requestDTO);
		return ResponseEntity
			.created(Util.getURI(diaryId))
			.body(ApiResponse.success(SUCCESS_CREATE_CORRECTION.getMessage()));
	}
}
