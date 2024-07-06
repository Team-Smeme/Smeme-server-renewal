package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.persistence.postgresql.persistence.entity.TopicEntity;

public interface TopicCustomRepository {
    TopicEntity findRandom();
}
