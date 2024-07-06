package com.smeem.http.controller;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.port.input.TopicUseCase;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.application.port.input.dto.response.topic.RetrieveRandomTopicResponse;
import com.smeem.http.controller.docs.TopicApiDocs;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/topics")
public class TopicApi implements TopicApiDocs {
    private final TopicUseCase topicUseCase;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/random")
    public SmeemResponse<RetrieveRandomTopicResponse> retrieveTopicByRandom() {
        return SmeemResponse.of(
                topicUseCase.retrieveRandomTopic(),
                SmeemMessage.RETRIEVE_TOPIC);
    }
}
