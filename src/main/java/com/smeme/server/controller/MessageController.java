package com.smeme.server.controller;

import static com.smeme.server.util.message.ResponseMessage.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.dto.message.MessageRequestDTO;
import com.smeme.server.service.MessageService;
import com.smeme.server.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/messages")
public class MessageController {

	private final MessageService messageService;

	@PostMapping
	public ResponseEntity<ApiResponse> pushMessage(@RequestBody MessageRequestDTO requestDto) throws Exception {
		messageService.pushMessage(requestDto);
		ApiResponse success = ApiResponse.success(SUCCESS_PUSH_MESSAGE.getMessage());
		return ResponseEntity.ok(success);
	}
}
