package com.smeem.application.domain.trainingtime;

import lombok.Getter;

@Getter
public class TrainingTime {
    Long id;
    DayType dayType;
    int hour;
    int minute;
    long memberId;
}
