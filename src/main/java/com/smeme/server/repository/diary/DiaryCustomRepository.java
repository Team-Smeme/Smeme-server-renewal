package com.smeme.server.repository.diary;

import java.time.LocalDateTime;
import java.util.List;

import com.smeme.server.model.Diary;
import com.smeme.server.model.Member;

public interface DiaryCustomRepository {
    boolean existTodayDiary(Member member);

    List<Diary> findDiariesStartToEnd(Member member, LocalDateTime startDate, LocalDateTime endDate);

    boolean exist30PastDiary(Member member);

    List<Diary> findDiariesDeleted30Past(LocalDateTime past);

    boolean existDiaryInDate(Member member, LocalDateTime createdDate);
}
