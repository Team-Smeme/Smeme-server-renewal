package com.smeme.server.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.dtos.fcm.MessageRequestDto;
import com.smeme.server.services.FcmService;
import com.smeme.server.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class FcmController {

	private final FcmService fcmService;

	@PostMapping("/api/fcm")
	public ResponseEntity<ApiResponse> pushMessage(@RequestBody MessageRequestDto requestDTO) throws IOException {
		fcmService.sendMessageTo(requestDTO.targetToken(), requestDTO.title(), requestDTO.body());
		return ResponseEntity.ok().build();
	}
}
