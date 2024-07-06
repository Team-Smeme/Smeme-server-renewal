package com.smeem.persistence.postgresql.persistence.repository;

import com.querydsl.core.types.dsl.NumberExpression;
import com.smeem.persistence.postgresql.persistence.entity.TopicEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import static com.smeem.persistence.postgresql.persistence.entity.QTopicEntity.topicEntity;

@Repository
@RequiredArgsConstructor
public class TopicRepositoryImpl implements TopicCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public TopicEntity findRandom() {
        return queryFactory
                .selectFrom(topicEntity)
                .orderBy(NumberExpression.random().asc())
                .limit(1)
                .fetchFirst();
    }
}
