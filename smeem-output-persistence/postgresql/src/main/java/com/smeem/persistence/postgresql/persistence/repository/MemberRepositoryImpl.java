package com.smeem.persistence.postgresql.persistence.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smeem.application.domain.trainingtime.DayType;
import com.smeem.persistence.postgresql.persistence.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.smeem.persistence.postgresql.persistence.entity.QMemberEntity.memberEntity;
import static com.smeem.persistence.postgresql.persistence.entity.QTrainingTimeEntity.trainingTimeEntity;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberEntity> findByTrainingTime(LocalDateTime trainingTime) {
        return queryFactory
                .selectFrom(memberEntity)
                .join(memberEntity).on(trainingTimeEntity.memberId.eq(memberEntity.id))
                .where(
                        trainingTimeEntity.memberId.eq(memberEntity.id),
                        trainingTimeEntity.dayType.eq(DayType.fromValue(trainingTime.getDayOfWeek().getValue())),
                        trainingTimeEntity.hour.eq(trainingTime.getHour()),
                        trainingTimeEntity.minute.eq(trainingTime.getMinute()))
                .fetch();
    }
}
