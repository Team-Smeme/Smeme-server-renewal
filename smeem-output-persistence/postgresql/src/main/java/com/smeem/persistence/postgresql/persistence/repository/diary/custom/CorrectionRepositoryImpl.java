package com.smeem.persistence.postgresql.persistence.repository.diary.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CorrectionRepositoryImpl implements CorrectionCustomRepository {
    private final JPAQueryFactory queryFactory;
}
