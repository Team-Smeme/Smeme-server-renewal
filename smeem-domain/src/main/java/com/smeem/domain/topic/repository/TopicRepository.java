package com.smeem.domain.topic.repository;

import com.smeem.domain.topic.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long>, TopicCustomRepository {
}
