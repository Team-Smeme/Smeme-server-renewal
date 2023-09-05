package com.smeme.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smeme.server.dto.topic.TopicResponseDTO;
import com.smeme.server.model.topic.Topic;
import com.smeme.server.repository.topic.TopicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicService {

	private final TopicRepository topicRepository;

	public TopicResponseDTO getRandomTopic() {
		Topic topic = topicRepository.getRandomTopic();
		return TopicResponseDTO.of(topic);
	}
}
