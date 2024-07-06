package com.smeem.application.domain.trainingtime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TrainingTime {
    Long id;
    DayType dayType;
    int hour;
    int minute;
    long memberId;

    public TrainingTime(DayType dayType, int hour, int minute) {
        this.dayType = dayType;
        this.hour = hour;
        this.minute = minute;
    }
}
