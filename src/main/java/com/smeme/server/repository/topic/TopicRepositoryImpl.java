package com.smeme.server.repository.topic;

import static com.querydsl.core.types.dsl.NumberExpression.*;
import static com.smeme.server.model.topic.QTopic.*;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smeme.server.model.topic.Topic;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TopicRepositoryImpl implements TopicCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Topic getRandom() {
        return queryFactory
                .select(topic)
                .from(topic)
                .orderBy(random().asc())
                .limit(1)
                .fetchFirst();
    }
}
