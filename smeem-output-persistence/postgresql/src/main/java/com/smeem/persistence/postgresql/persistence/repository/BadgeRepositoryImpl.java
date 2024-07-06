package com.smeem.persistence.postgresql.persistence.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smeem.persistence.postgresql.persistence.entity.BadgeEntity;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.smeem.persistence.postgresql.persistence.entity.QBadgeEntity.badgeEntity;

@Repository
@RequiredArgsConstructor
public class BadgeRepositoryImpl implements BadgeCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BadgeEntity> findAllOrderById() {
        return queryFactory
                .selectFrom(badgeEntity)
                .orderBy(badgeEntity.id.asc())
                .fetch();
    }
}
