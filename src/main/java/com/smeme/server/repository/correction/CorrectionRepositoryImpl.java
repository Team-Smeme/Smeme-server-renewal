package com.smeme.server.repository.correction;

import static com.smeme.server.model.QCorrection.*;
import static com.smeme.server.model.QDiary.*;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smeme.server.model.Member;
import com.smeme.server.model.QDiary;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CorrectionRepositoryImpl implements CorrectionCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public int countCorrection(Member member) {
        return Math.toIntExact(queryFactory
                .select(correction.count())
                .from(correction)
                .leftJoin(correction.diary, diary)
                .where(
                        diary.member.eq(member),
                        diary.isDeleted.eq(false)
                )
                .fetchFirst());
    }
}
