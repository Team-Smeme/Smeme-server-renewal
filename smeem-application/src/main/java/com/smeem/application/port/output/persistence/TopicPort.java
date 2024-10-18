package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.topic.Topic;

public interface TopicPort {
    Topic findById(long id);
    Topic findRandom();

    void checkValidation(long topicId);
}
