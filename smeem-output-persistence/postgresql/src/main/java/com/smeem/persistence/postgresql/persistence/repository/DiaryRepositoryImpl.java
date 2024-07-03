package com.smeem.persistence.postgresql.persistence.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smeem.persistence.postgresql.persistence.entity.DiaryEntity;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.smeem.persistence.postgresql.persistence.entity.QDiaryEntity.diaryEntity;

@Repository
@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByMemberIdAndYesterday(long memberId) {
        val today = LocalDate.now().atStartOfDay();
        return queryFactory
                .selectFrom(diaryEntity)
                .where(
                        diaryEntity.memberId.eq(memberId),
                        diaryEntity.createdAt.between(today.minusDays(1), today)
                )
                .fetchFirst() != null;
    }

    @Override
    public List<DiaryEntity> findByMemberIdBetween(long memberId, LocalDate startAt, LocalDate endAt) {
        return queryFactory
                .selectFrom(diaryEntity)
                .where(
                        diaryEntity.memberId.eq(memberId),
                        diaryEntity.createdAt.between(startAt.atStartOfDay(), endAt.atStartOfDay().plusDays(1)))
                .fetch();
    }

    @Override
    public boolean existsByMemberAndPastAgo(long memberId, int days) {
        val remindAt = LocalDate.now().minusDays(days).atStartOfDay();
        return queryFactory
                .selectFrom(diaryEntity)
                .where(
                        diaryEntity.memberId.eq(memberId),
                        diaryEntity.createdAt.between(remindAt, remindAt.plusDays(1)))
                .fetchFirst() != null;
    }
}
