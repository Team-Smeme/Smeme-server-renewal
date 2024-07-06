package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.persistence.postgresql.persistence.entity.DeletedDiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DeletedDiaryRepository extends JpaRepository<DeletedDiaryEntity, Long> {
    void deleteByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
    void deleteByMemberId(long memberId);
}
