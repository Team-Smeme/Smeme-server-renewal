package com.smeem.api.topic.controller;

import com.smeem.api.common.ApiResponseUtil;
import com.smeem.api.common.BaseResponse;
import com.smeem.api.topic.controller.dto.response.TopicResponseDTO;
import com.smeem.api.topic.service.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static com.smeem.common.code.success.TopicSuccessCode.SUCCESS_GET_RANDOM_TOPIC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/topics")
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/random")
    public ResponseEntity<BaseResponse<?>> getRandom() {
        TopicResponseDTO response = topicService.getRandom();
        return ApiResponseUtil.success(SUCCESS_GET_RANDOM_TOPIC, response);
    }
}
