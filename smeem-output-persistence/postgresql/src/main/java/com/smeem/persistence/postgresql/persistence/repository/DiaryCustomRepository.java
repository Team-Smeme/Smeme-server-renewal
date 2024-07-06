package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.persistence.postgresql.persistence.entity.DiaryEntity;

import java.time.LocalDate;
import java.util.List;

public interface DiaryCustomRepository {
    boolean existsByMemberIdAndYesterday(long memberId);

    List<DiaryEntity> findByMemberIdBetween(long memberId, LocalDate startAt, LocalDate endAt);

    boolean existsByMemberAndPastAgo(long memberId, int days);

    long countWeeklyByMemberId(long memberId);
}
