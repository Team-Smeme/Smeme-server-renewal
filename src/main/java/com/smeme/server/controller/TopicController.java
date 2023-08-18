package com.smeme.server.controller;

import static com.smeme.server.util.message.ResponseMessage.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.dto.topic.TopicResponseDTO;
import com.smeme.server.service.TopicService;
import com.smeme.server.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/topics")
@SecurityRequirement(name = "Authorization")
public class TopicController {

	private final TopicService topicService;

	@GetMapping("/random")
	public ResponseEntity<ApiResponse> getRandomTopic() {
		TopicResponseDTO response = topicService.getRandomTopic();
		return ResponseEntity.ok(ApiResponse.success(SUCCESS_GET_RANDOM_TOPIC.getMessage(), response));
	}
}
