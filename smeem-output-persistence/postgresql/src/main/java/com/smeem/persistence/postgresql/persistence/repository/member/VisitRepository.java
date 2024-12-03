package com.smeem.persistence.postgresql.persistence.repository.member;

import com.smeem.persistence.postgresql.persistence.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<VisitEntity, Long> {
    Optional<VisitEntity> findByDate(LocalDate date);
}
