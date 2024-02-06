package com.smeem.topic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.model.topic.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long>, TopicCustomRepository {
}
