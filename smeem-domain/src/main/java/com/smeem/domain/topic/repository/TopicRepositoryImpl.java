package com.smeem.domain.topic.repository;


import com.querydsl.core.types.dsl.NumberExpression;
import com.smeem.domain.topic.model.QTopic;
import com.smeem.domain.topic.model.Topic;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TopicRepositoryImpl implements TopicCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Topic getRandom() {
        return queryFactory
                .select(QTopic.topic)
                .from(QTopic.topic)
                .orderBy(NumberExpression.random().asc())
                .limit(1)
                .fetchFirst();
    }
}
