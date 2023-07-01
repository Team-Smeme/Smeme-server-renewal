package com.smeme.server.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.service.MessageService;
import com.smeme.server.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/test")
public class TestController {
	private final MessageService messageService;

	@Value("${fcm.smeem_title}")
	private String MESSAGE_TITLE;
	@Value("${fcm.smeem_body}")
	private String MESSAGE_BODY;

	@GetMapping
	public ResponseEntity<ApiResponse> test() {
		return ResponseEntity.ok(ApiResponse.success("server connect"));
	}

	@GetMapping("/alarm")
	public ResponseEntity<ApiResponse> alarmTest(Principal principal) {
		messageService.pushTest(MESSAGE_TITLE, MESSAGE_BODY, Long.valueOf(principal.getName()));
		return ResponseEntity.ok(ApiResponse.success("푸시 알림 전송 성공"));
	}
}
