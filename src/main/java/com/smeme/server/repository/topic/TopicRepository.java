package com.smeme.server.repository.topic;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.model.topic.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long>, TopicCustomRepository {
}