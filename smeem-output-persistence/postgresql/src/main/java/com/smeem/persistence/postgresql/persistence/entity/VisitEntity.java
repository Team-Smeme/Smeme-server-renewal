package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.visit.Visit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VisitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private long memberId;
    @Column(nullable = false)
    private LocalDate visitedAt;

    public VisitEntity(Visit visit) {
        this.memberId = visit.getMemberId();
        this.visitedAt = visit.getVisitedAt();
    }
}
