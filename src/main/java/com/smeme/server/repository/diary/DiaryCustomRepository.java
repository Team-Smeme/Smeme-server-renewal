package com.smeme.server.repository.diary;

import com.smeme.server.model.Member;

public interface DiaryCustomRepository {
	boolean existTodayDiary(Member member);
}
