package com.smeem.domain.member.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import java.time.LocalDate;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PACKAGE;

@Embeddable
@Getter
@AllArgsConstructor(access = PACKAGE)
public class DiaryComboInfo {

    private int diaryComboCount;

    private LocalDate lastComboAt;

    public DiaryComboInfo() {
        this.diaryComboCount = 0;
    }

    public void update() {
        val today = LocalDate.now();
        if (isNull(lastComboAt)) {
            this.diaryComboCount = 1;
        } else if (!lastComboAt.equals(today)) {
            val yesterday = today.minusDays(1);
            this.diaryComboCount = lastComboAt.equals(yesterday) ? this.diaryComboCount + 1 : 1;
        }
        this.lastComboAt = today;
    }
}
