package com.smeem.persistence.postgresql.persistence.repository.diary.custom;

import java.time.LocalDate;

public interface CorrectionCustomRepository {
    int countDistinctKeyByMemberIdAndCreatedAt(long memberId, LocalDate date);
}
