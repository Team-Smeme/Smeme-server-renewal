package com.smeem.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smeem.common.exception.TrainingTimeException;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.training.model.DayType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.smeem.common.code.failure.TrainingTimeFailureCode.INVALID_DAY_OF_WEEK;
import static com.smeem.domain.member.model.QMember.member;
import static com.smeem.domain.training.model.DayType.*;
import static com.smeem.domain.training.model.QTrainingTime.trainingTime;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findAllByTrainingTime(LocalDateTime now) {
        return queryFactory
                .selectFrom(member)
                .join(member.trainingTimes, trainingTime).fetchJoin().distinct()
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
