package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.port.output.persistence.VisitPort;
import com.smeem.persistence.postgresql.persistence.entity.VisitEntity;
import com.smeem.persistence.postgresql.persistence.repository.member.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class VisitAdapter implements VisitPort {
    private final VisitRepository visitRepository;

    @Override
    public void update(LocalDate date, String bitmap) {
        VisitEntity visit = visitRepository.findByDate(date)
                .orElseGet(() -> visitRepository.save(new VisitEntity(date, bitmap)));
        visit.update(bitmap);
    }
}
