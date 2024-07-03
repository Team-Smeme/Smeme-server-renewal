package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.topic.Topic;
import com.smeem.application.port.output.persistence.TopicPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.persistence.postgresql.persistence.entity.TopicEntity;
import com.smeem.persistence.postgresql.persistence.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TopicAdapter implements TopicPort {
    private final TopicRepository topicRepository;

    @Override
    public Topic findById(long id) {
        return find(id).toDomain();
    }

    private TopicEntity find(long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new SmeemException(ExceptionCode.NOT_FOUND, "id: " + id));
    }
}
