package com.smeme.server.repository.diary;

import static com.smeme.server.model.QCorrection.correction;
import static com.smeme.server.model.QDiary.*;
import static com.smeme.server.util.Util.getStartOfDay;
import static java.lang.Integer.parseInt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.smeme.server.config.ValueConfig;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smeme.server.model.Diary;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final ValueConfig valueConfig;

    @Override
    public List<Diary> findByExpiredDate() {
        int expiredDay = parseInt(valueConfig.getDURATION_EXPIRED());
        LocalDateTime expiredDate = getStartOfDay(LocalDateTime.now()).minusDays(expiredDay - 1);

        return queryFactory
                .selectFrom(diary)
                .where(
                        diary.isDeleted.eq(true),
                        diary.createdAt.before(expiredDate)
                )
                .fetch();
    }

    @Override
    public Optional<Diary> findByIdFetchJoinCorrections(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(diary)
                .where(diary.id.eq(id))
                .leftJoin(diary.corrections, correction).fetchJoin().distinct()
                .fetchFirst());
    }
}
