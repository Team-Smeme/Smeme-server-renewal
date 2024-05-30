package com.smeem.domain.visit.repository;

import com.smeem.domain.member.model.Member;
import com.smeem.domain.visit.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    boolean existsByMemberAndVisitedAtBetween(Member member, LocalDateTime start, LocalDateTime end);
    int countByMember(Member member);
    void deleteByMember(Member member);
}
