package com.smeem.topic.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smeme.server.dto.topic.TopicResponseDTO;
import com.smeme.server.model.topic.Topic;
import com.smeme.server.repository.topic.TopicRepository;

import lombok.RequiredArgsConstructor;

import static com.smeme.server.util.message.ErrorMessage.INVALID_TOPIC;
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

    protected Topic get(Long id) {
        return nonNull(id)
                ? topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.INVALID_TOPIC.getMessage()))
                : null;
    }
}
