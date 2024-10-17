package com.smeem.persistence.postgresql.persistence.repository.diary;

import com.smeem.persistence.postgresql.persistence.entity.CorrectionEntity;
import com.smeem.persistence.postgresql.persistence.repository.diary.custom.CorrectionCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorrectionRepository extends JpaRepository<CorrectionEntity, Long>, CorrectionCustomRepository {
}
