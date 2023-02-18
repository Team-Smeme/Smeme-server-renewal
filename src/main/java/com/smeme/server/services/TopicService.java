package com.smeme.server.services;

import com.smeme.server.dtos.topic.TopicResponseDto;
import com.smeme.server.models.Topic;
import com.smeme.server.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class TopicService {

    private final TopicRepository topicRepository;

    @Transactional
    public TopicResponseDto findTopicByRandom() {
        List<Topic> topics = topicRepository.findAll()
                .stream().filter(topic -> topic.getId() != 0)
                .toList();

        int randomNumber = new Random().nextInt(topics.size());

        return TopicResponseDto.from(topics.get(randomNumber));
    }
}
