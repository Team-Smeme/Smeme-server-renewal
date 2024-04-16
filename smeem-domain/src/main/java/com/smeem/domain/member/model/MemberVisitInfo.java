package com.smeem.domain.member.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.val;

import java.time.LocalDate;

@Embeddable
@Getter
public class MemberVisitInfo {

    private Integer visitCount;

    private LocalDate lastVisitAt;

    protected MemberVisitInfo() {
        this.visitCount = 1;
        this.lastVisitAt = LocalDate.now();
    }

    protected void updateToday() {
        val today = LocalDate.now();
        if (!lastVisitAt.equals(today)) {
            visitCount++;
            lastVisitAt = today;
        }
    }
}
