package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.visit.Visit;
import com.smeem.application.port.output.persistence.VisitPort;
import com.smeem.persistence.postgresql.persistence.entity.VisitEntity;
import com.smeem.persistence.postgresql.persistence.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class VisitAdapter implements VisitPort {
    private final VisitRepository visitRepository;

    @Override
    public void visit(Visit visit) {
        visitRepository.save(new VisitEntity(visit));
    }

    @Override
    public boolean isExistByMemberAndToday(long memberId) {
        return visitRepository.existsByMemberIdAndVisitedAt(memberId, LocalDate.now());
    }
}
