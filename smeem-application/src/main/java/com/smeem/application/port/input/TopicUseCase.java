package com.smeem.application.port.input;

import com.smeem.application.port.input.dto.response.topic.RetrieveRandomTopicResponse;

public interface TopicUseCase {
    RetrieveRandomTopicResponse retrieveRandomTopic();
}
