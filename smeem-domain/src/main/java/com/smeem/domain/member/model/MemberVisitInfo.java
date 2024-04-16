package com.smeem.domain.member.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.val;

import java.time.LocalDate;

import static java.util.Objects.isNull;

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
        System.out.println("test");
        val today = LocalDate.now();
        if (isNull(lastVisitAt)) {
            this.visitCount = 1;
        } else if (!lastVisitAt.equals(today)) {
            this.visitCount++;
        }
        this.lastVisitAt = today;
    }
}
