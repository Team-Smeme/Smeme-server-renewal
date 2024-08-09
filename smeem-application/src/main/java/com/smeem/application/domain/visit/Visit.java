package com.smeem.application.domain.visit;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Visit {
    Long id;
    long memberId;
    LocalDate visitedAt;

    public Visit(long memberId) {
        this.memberId = memberId;
        this.visitedAt = LocalDate.now();
    }
}
