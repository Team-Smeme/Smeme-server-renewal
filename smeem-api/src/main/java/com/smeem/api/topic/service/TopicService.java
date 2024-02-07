package com.smeem.api.topic.service;

import com.smeem.api.topic.controller.dto.response.TopicResponseDTO;
import com.smeem.common.exception.TopicException;
import com.smeem.domain.topic.model.Topic;
import com.smeem.domain.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

import static com.smeem.common.code.failure.TopicFailureCode.INVALID_TOPIC;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicResponseDTO getRandom() {
        Topic topic = topicRepository.getRandom();
        return TopicResponseDTO.of(topic);
    }

    public Topic get(Long id) {
        return nonNull(id)
                ? topicRepository.findById(id)
                .orElseThrow(() -> new TopicException(INVALID_TOPIC))
                : null;
    }
}
