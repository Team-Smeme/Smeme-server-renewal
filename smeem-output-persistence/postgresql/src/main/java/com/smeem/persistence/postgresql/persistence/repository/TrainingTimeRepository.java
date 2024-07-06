package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.persistence.postgresql.persistence.entity.TrainingTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingTimeRepository extends JpaRepository<TrainingTimeEntity, Long> {
    List<TrainingTimeEntity> findByMemberId(long memberId);
    void deleteByMemberId(long memberId);
}
