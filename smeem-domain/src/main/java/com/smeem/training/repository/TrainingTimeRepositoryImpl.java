package com.smeem.training.repository;

import static com.smeme.server.model.QMember.*;
import static com.smeme.server.model.training.DayType.*;
import static com.smeme.server.model.training.QTrainingTime.*;
import static com.smeme.server.util.message.ErrorMessage.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smeme.server.model.training.DayType;
import com.smeme.server.model.training.TrainingTime;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TrainingTimeRepositoryImpl implements TrainingTimeCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TrainingTime> getTrainingTimeForPushAlarm(LocalDateTime now) {
        return queryFactory
                .select(QTrainingTime.trainingTime)
                .from(QTrainingTime.trainingTime)
                .join(QTrainingTime.trainingTime.member, QMember.member).fetchJoin()
                .where(
                        QTrainingTime.trainingTime.day.eq(getDayType(now.getDayOfWeek().getValue())),
                        QTrainingTime.trainingTime.hour.eq(now.getHour()),
                        QTrainingTime.trainingTime.minute.eq(now.getMinute()),
                        QMember.member.hasPushAlarm.eq(true)
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
            default -> throw new RuntimeException(ErrorMessage.INVALID_DAY_OF_WEEK.getMessage());
        };
    }
}
