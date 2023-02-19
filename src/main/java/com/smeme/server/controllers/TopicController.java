package com.smeme.server.controllers;

import com.smeme.server.dtos.topic.TopicResponseDto;
import com.smeme.server.services.TopicService;
import com.smeme.server.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topics")
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<ApiResponse> findTopicByRandom() {
        TopicResponseDto topicResponseDto = topicService.findTopicByRandom();

        ApiResponse apiResponse = ApiResponse.of(
                HttpStatus.OK.value(), true, "랜덤 주제 조회 성공", topicResponseDto);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
