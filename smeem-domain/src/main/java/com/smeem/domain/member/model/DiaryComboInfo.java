package com.smeem.domain.member.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import java.time.LocalDate;

import static lombok.AccessLevel.PACKAGE;

@Embeddable
@Getter
@AllArgsConstructor(access = PACKAGE)
public class DiaryComboInfo {

    private int diaryComboCount;

    private LocalDate lastComboAt; //TODO: ??, (어제 일기 썼는지..) DB 마이그레이션 필요? (하겠지 ㅋㅋ ㅠㅠ)

    public DiaryComboInfo() {
        this.diaryComboCount = 0;
    }

    public void update() {
        val today = LocalDate.now();
        if (!lastComboAt.equals(today)) { //TODO: null exception 안 나는 지 확인 필요
            val yesterday = today.minusDays(1);
            this.diaryComboCount = lastComboAt.equals(yesterday) ? this.diaryComboCount + 1 : 1;
            this.lastComboAt = today;
        }
    }
}
