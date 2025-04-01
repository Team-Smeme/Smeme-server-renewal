package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.persistence.postgresql.persistence.entity.CoachingSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachingSurveyRepository extends JpaRepository<CoachingSurveyEntity, Long> {
}
