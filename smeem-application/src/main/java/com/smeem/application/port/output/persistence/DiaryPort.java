package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.diary.Diary;

import java.time.LocalDate;
import java.util.List;

public interface DiaryPort {
    int countByMember(long memberId);
    Diary save(Diary diary);
    boolean isExistByMemberAndYesterday(long memberId);
    Diary findById(long id);
    Diary update(Diary diary);
    void softDelete(long id);
    List<Diary> findByMemberAndTerm(long memberId, LocalDate startAt, LocalDate endAt);
    boolean isExistByMemberAndPastAgo(long memberId, int days);
    int countWeeklyByMember(long memberId);
    void deleteByCreatedAt(LocalDate date);
    boolean existsById(long id);
}
