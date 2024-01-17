package com.smeme.server.controller;

import static com.smeme.server.util.ApiResponse.success;
import static com.smeme.server.util.message.ResponseMessage.*;

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
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/random")
    public ResponseEntity<ApiResponse> getRandom() {
        TopicResponseDTO response = topicService.getRandom();
        return ResponseEntity.ok(success(SUCCESS_GET_RANDOM_TOPIC.getMessage(), response));
    }
}
