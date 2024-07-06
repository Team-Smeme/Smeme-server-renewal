package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.persistence.postgresql.persistence.entity.DeletedDiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedDiaryRepository extends JpaRepository<DeletedDiaryEntity, Long> {
}
