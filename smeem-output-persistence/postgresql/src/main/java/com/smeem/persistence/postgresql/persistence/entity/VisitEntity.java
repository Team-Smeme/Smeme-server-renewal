package com.smeem.persistence.postgresql.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate date;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String log;

    public VisitEntity(LocalDate date, String log) {
        this.date = date;
        this.log = log;
    }

    public void update(String log) {
        this.log = log;
    }
}
