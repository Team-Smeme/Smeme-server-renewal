package com.smeem.api.topic.service;

import com.smeem.api.topic.service.dto.response.RandomTopicGetServiceResponse;
import com.smeem.domain.topic.repository.TopicRepository;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicService {

    private final TopicRepository topicRepository;

    public RandomTopicGetServiceResponse getTopicByRandom() {
        val topic = topicRepository.findByRandom();
        return RandomTopicGetServiceResponse.of(topic);
    }
}
