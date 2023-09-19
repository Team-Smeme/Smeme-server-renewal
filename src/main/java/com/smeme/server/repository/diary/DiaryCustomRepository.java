package com.smeme.server.repository.diary;

import java.time.LocalDateTime;
import java.util.List;

import com.smeme.server.model.Diary;
import com.smeme.server.model.Member;
import org.springframework.data.jpa.repository.Modifying;

public interface DiaryCustomRepository {
    boolean existTodayDiary(Member member);

    List<Diary> findDiariesStartToEnd(Member member, LocalDateTime startDate, LocalDateTime endDate);

    boolean exist30PastDiary(Member member);

    List<Diary> findDiariesDeleted30Past(LocalDateTime past);

    boolean existDiaryInDate(Member member, LocalDateTime createdDate);

    @Modifying(clearAutomatically = true)
    void deleteByMemberAndExpired(Member member); // 확인하고 member 빼기, 이름 바꾸기
}
