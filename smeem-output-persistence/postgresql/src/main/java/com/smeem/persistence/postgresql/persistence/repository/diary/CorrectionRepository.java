package com.smeem.persistence.postgresql.persistence.repository.diary;

import com.smeem.persistence.postgresql.persistence.entity.CorrectionEntity;
import com.smeem.persistence.postgresql.persistence.repository.diary.custom.CorrectionCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorrectionRepository extends JpaRepository<CorrectionEntity, Long>, CorrectionCustomRepository {
    void deleteByDiaryId(long diaryId);

    void deleteByMemberId(long memberId);

    List<CorrectionEntity> findByDiaryId(long diaryId);
}
