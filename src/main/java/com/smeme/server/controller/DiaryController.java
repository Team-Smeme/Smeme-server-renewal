package com.smeme.server.controller;

import static com.smeme.server.util.message.ErrorMessage.*;
import static com.smeme.server.util.message.ResponseMessage.*;
import static java.util.Objects.*;

import java.net.URI;
import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smeme.server.dto.diary.DiaryRequestDTO;
import com.smeme.server.service.DiaryService;
import com.smeme.server.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/diaries")
public class DiaryController {

	private final DiaryService diaryService;

	@PostMapping
	public ResponseEntity<ApiResponse> createDiary(Principal principal, @RequestBody DiaryRequestDTO requestDTO) {
		Long memberId = getMemberId(principal);
		Long diaryId = diaryService.createDiary(memberId, requestDTO);
		return ResponseEntity
			.created(getURI(diaryId))
			.body(ApiResponse.success(SUCCESS_CREATE_DIARY.getMessage()));
	}

	private Long getMemberId(Principal principal) {
		if (isNull(principal)) {
			throw new SecurityException(EMPTY_ACCESS_TOKEN.getMessage());
		}
		return Long.valueOf(principal.getName());
	}

	private URI getURI(Long diaryId) {
		return ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{diaryId}")
			.buildAndExpand(diaryId)
			.toUri();
	}
}
