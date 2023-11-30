package com.smeme.server.controller;

import static com.smeme.server.util.ApiResponse.success;
import static com.smeme.server.util.message.ResponseMessage.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.dto.topic.TopicResponseDTO;
import com.smeme.server.service.TopicService;
import com.smeme.server.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Topic", description = "랜덤 주제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/topics")
@SecurityRequirement(name = "Authorization")
public class TopicController {

    private final TopicService topicService;

    @Operation(summary = "랜덤 주제 조회", description = "랜덤 주제를 임의로 조회합니다.")
    @GetMapping("/random")
    public ResponseEntity<ApiResponse> getRandom() {
        TopicResponseDTO response = topicService.getRandom();
        return ResponseEntity.ok(success(SUCCESS_GET_RANDOM_TOPIC.getMessage(), response));
    }
}
