package com.smeem.api.topic.controller;



import com.smeem.api.common.ApiResponse;
import com.smeem.api.topic.controller.dto.response.TopicResponseDTO;
import com.smeem.api.topic.service.TopicService;
import com.smeem.common.code.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/topics")
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/random")
    public ResponseEntity<ApiResponse> getRandom() {
        TopicResponseDTO response = topicService.getRandom();
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.SUCCESS_GET_RANDOM_TOPIC.getMessage(), response));
    }
}
