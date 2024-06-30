package com.smeem.domain.visit.repository;

import com.smeem.domain.persistence.entity.MemberEntity;
import com.smeem.domain.visit.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    boolean existsByMemberAndVisitedAtBetween(MemberEntity member, LocalDateTime start, LocalDateTime end);
    int countByMember(MemberEntity member);
    void deleteAllByMember(MemberEntity member);
}
