package com.smeem.domain.training.repository;

import static com.smeem.common.code.failure.TrainingTimeFailureCode.INVALID_DAY_OF_WEEK;
import static com.smeem.domain.member.model.QMember.member;
import static com.smeem.domain.training.model.DayType.*;
import static com.smeem.domain.training.model.QTrainingTime.trainingTime;

import java.time.LocalDateTime;
import java.util.List;

import com.smeem.common.exception.TrainingTimeException;
import com.smeem.domain.training.model.DayType;
import com.smeem.domain.training.model.TrainingTime;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TrainingTimeRepositoryImpl implements TrainingTimeCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TrainingTime> getTrainingTimeForPushAlarm(LocalDateTime now) {
        return queryFactory
                .select(trainingTime)
                .from(trainingTime)
                .join(trainingTime.member, member).fetchJoin()
                .where(
                        trainingTime.day.eq(getDayType(now.getDayOfWeek().getValue())),
                        trainingTime.hour.eq(now.getHour()),
                        trainingTime.minute.eq(now.getMinute()),
                        member.hasPushAlarm.eq(true)
                )
                .fetch();
    }

    private DayType getDayType(int dayValue) {
        return switch (dayValue) {
            case 1 -> MON;
            case 2 -> TUE;
            case 3 -> WED;
            case 4 -> THU;
            case 5 -> FRI;
            case 6 -> SAT;
            case 7 -> SUN;
            default -> throw new TrainingTimeException(INVALID_DAY_OF_WEEK);
        };
    }
}
