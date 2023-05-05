package com.smeme.server.repository.diary;

import static com.smeme.server.model.QDiary.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smeme.server.model.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public boolean existTodayDiary(Member member) {
		LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
		return queryFactory
			.from(diary)
			.where(
				diary.member.eq(member),
				diary.createdAt.between(get12midnight(now), now)
			)
			.select(diary.id)
			.fetchFirst() != null;
	}

	private LocalDateTime get12midnight(LocalDateTime now) {
		return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
	}
}
