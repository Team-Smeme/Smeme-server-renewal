package com.smeem.domain.history.repository;

import com.smeem.domain.history.model.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
}
