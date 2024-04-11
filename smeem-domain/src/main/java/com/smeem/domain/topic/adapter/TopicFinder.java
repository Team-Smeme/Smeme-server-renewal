package com.smeem.domain.topic.adapter;

import com.smeem.domain.support.RepositoryAdapter;
import com.smeem.domain.topic.exception.TopicException;
import com.smeem.domain.topic.model.Topic;
import com.smeem.domain.topic.repository.TopicRepository;
import lombok.RequiredArgsConstructor;

import static com.smeem.common.code.failure.TopicFailureCode.INVALID_TOPIC;

@RepositoryAdapter
@RequiredArgsConstructor
public class TopicFinder {

    private final TopicRepository topicRepository;

    public Topic findById(final long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new TopicException(INVALID_TOPIC));
    }

    public Topic findByRandom() {
        return topicRepository.findByRandom();
    }
}
