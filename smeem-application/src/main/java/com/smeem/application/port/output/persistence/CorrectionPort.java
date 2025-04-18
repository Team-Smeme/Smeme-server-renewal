package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.diary.Correction;
import com.smeem.application.domain.diary.Diary;

import java.time.LocalDate;
import java.util.List;

public interface CorrectionPort {
    List<Correction> save(List<Correction> corrections, Diary diary);

    int countDistinctByMemberAndDate(long memberId, LocalDate date);

    int countByMember(long memberId);

    List<Correction> findByDiary(long diary);

    void deleteByDiary(long diaryId);

    void deleteByMember(long memberId);
}
