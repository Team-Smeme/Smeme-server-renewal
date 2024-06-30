package com.smeem.domain.persistence.repository.badge;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smeem.domain.persistence.entity.BadgeEntity;
import com.smeem.domain.badge.model.QBadge;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class BadgeRepositoryImpl implements BadgeCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BadgeEntity> findAllOrderById() {
        return queryFactory
                .selectFrom(QBadge.badge)
                .orderBy(QBadge.badge.id.asc())
                .fetch();
    }
}
