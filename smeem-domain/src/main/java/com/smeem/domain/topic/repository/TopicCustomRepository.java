package com.smeem.domain.topic.repository;


import com.smeem.domain.topic.model.Topic;

public interface TopicCustomRepository {
    Topic findByRandom();
}
