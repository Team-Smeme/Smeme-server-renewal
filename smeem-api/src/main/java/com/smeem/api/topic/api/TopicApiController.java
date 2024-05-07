package com.smeem.api.topic.api;

import com.smeem.api.support.ApiResponseGenerator;
import com.smeem.api.common.SuccessResponse;
import com.smeem.api.topic.api.dto.response.RandomTopicGetResponse;
import com.smeem.api.topic.service.TopicService;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static com.smeem.common.code.success.TopicSuccessCode.SUCCESS_GET_RANDOM_TOPIC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/topics")
public class TopicApiController implements TopicApi {

    private final TopicService topicService;

    @GetMapping("/random")
    public ResponseEntity<SuccessResponse<RandomTopicGetResponse>> getTopicByRandom() {
        val response = RandomTopicGetResponse.from(topicService.getTopicByRandom());
        return ApiResponseGenerator.success(SUCCESS_GET_RANDOM_TOPIC, response);
    }
}
