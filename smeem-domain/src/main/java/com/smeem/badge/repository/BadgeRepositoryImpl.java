package com.smeem.badge.repository;

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
    public List<Badge> findAllOrderById() {
        return queryFactory
                .selectFrom(QBadge.badge)
                .orderBy(QBadge.badge.id.asc())
                .fetch();
    }
}
