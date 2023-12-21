package com.smeme.server.repository.diary;

import static com.smeme.server.model.QCorrection.correction;
import static com.smeme.server.model.QDiary.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smeme.server.model.Diary;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Diary> findByIdFetchJoinCorrections(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(diary)
                .where(diary.id.eq(id))
                .leftJoin(diary.corrections, correction).fetchJoin().distinct()
                .fetchFirst());
    }

    @Override
    public List<Diary> findDeleted() {
        return queryFactory
                .selectFrom(diary)
                .where(diary.isDeleted.eq(true))
                .fetch();
    }
}
