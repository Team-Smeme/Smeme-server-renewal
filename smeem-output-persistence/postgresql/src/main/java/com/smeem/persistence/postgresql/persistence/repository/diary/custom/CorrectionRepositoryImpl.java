package com.smeem.persistence.postgresql.persistence.repository.diary.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.smeem.persistence.postgresql.persistence.entity.QCorrectionEntity.correctionEntity;

@Repository
@RequiredArgsConstructor
public class CorrectionRepositoryImpl implements CorrectionCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public int countDistinctKeyByMemberIdAndCreatedAt(long memberId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return (int) queryFactory
                .select(correctionEntity.key)
                .from(correctionEntity)
                .where(
                        correctionEntity.createdAt.between(startOfDay, endOfDay),
                        correctionEntity.memberId.eq(memberId)
                )
                .distinct()
                .stream().count();
    }
}
