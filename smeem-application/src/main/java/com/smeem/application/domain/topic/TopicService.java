package com.smeem.application.domain.topic;

import com.smeem.application.port.input.TopicUseCase;
import com.smeem.application.port.input.dto.response.topic.RetrieveRandomTopicResponse;
import com.smeem.application.port.output.persistence.TopicPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicService implements TopicUseCase {
    private final TopicPort topicPort;

    @Override
    public RetrieveRandomTopicResponse retrieveRandomTopic() {
        return RetrieveRandomTopicResponse.of(topicPort.findRandom());
    }
}
