package com.smeme.server.repository.diary;

import static com.smeme.server.model.QDiary.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smeme.server.model.Diary;
import com.smeme.server.model.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public boolean existTodayDiary(Member member) {
		LocalDateTime now = LocalDateTime.now();
		return queryFactory
			.from(diary)
			.where(
				diary.member.eq(member),
				diary.createdAt.between(get12midnight(now), now)
			)
			.select(diary.id)
			.fetchFirst() != null;
	}

	@Override
	public List<Diary> findDiariesStartToEnd(Member member, LocalDateTime startDate, LocalDateTime endDate) {
		return queryFactory
			.select(diary)
			.from(diary)
			.where(
				diary.member.eq(member),
				diary.createdAt.between(startDate, endDate),
				diary.isDeleted.eq(false)
				)
			.fetch();
	}

	@Override
	public boolean exist30PastDiary(Member member) {
		LocalDateTime past = LocalDateTime.now().minusDays(30);
		return queryFactory
			.select(diary)
			.from(diary)
			.where(
				diary.member.eq(member),
				diary.createdAt.year().eq(past.getYear()),
				diary.createdAt.month().eq(past.getMonthValue()),
				diary.createdAt.dayOfMonth().eq(past.getDayOfMonth())
			)
			.fetchFirst() != null;
	}

	@Override
	public List<Diary> findDiariesDeleted30Past(LocalDateTime past) {
		return queryFactory
			.select(diary)
			.from(diary)
			.where(
				diary.isDeleted.eq(true),
				diary.updatedAt.year().eq(past.getYear()),
				diary.updatedAt.month().eq(past.getMonthValue()),
				diary.updatedAt.dayOfMonth().eq(past.getDayOfMonth())
			)
			.fetch();
	}

	private LocalDateTime get12midnight(LocalDateTime now) {
		return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
	}
}
