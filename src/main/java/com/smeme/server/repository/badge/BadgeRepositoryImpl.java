package com.smeme.server.repository.badge;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smeme.server.model.badge.Badge;
import lombok.*;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.smeme.server.model.badge.QBadge.badge;

@Repository
@RequiredArgsConstructor
public class BadgeRepositoryImpl implements BadgeCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Badge> findAllOrderByName() {
        val contentInKRExpression = Expressions.stringTemplate("SUBSTR({0}, 1, 1)", badge.name);
        return queryFactory
                .selectFrom(badge)
                .orderBy(contentInKRExpression.asc())
                .fetch();
    }
}
