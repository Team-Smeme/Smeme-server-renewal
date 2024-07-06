package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.persistence.postgresql.persistence.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface VisitRepository extends JpaRepository<VisitEntity, Long> {
    boolean existsByMemberIdAndVisitedAt(long memberId, LocalDate visitedAt);
    void deleteByMemberId(long memberId);
}
