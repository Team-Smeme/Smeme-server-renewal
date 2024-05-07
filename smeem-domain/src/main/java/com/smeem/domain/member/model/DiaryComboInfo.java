package com.smeem.domain.member.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.val;

import java.time.LocalDate;

import static java.util.Objects.isNull;

@Embeddable
@Getter
public class DiaryComboInfo {

    private int diaryComboCount;

    private LocalDate lastComboAt;

    protected DiaryComboInfo() {
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
