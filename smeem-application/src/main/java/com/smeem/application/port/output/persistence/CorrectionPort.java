package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.diary.Correction;
import com.smeem.application.domain.diary.Diary;

import java.time.LocalDate;
import java.util.List;

public interface CorrectionPort {
    List<Correction> saveAll(List<Correction> corrections, Diary diary);

    int countDistinctByMemberAndDate(long memberId, LocalDate date);

    List<Correction> findByDiary(long diary);

    void deleteByDiary(long diaryId);
}
