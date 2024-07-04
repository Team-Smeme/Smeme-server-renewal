package com.smeem.application.domain.trainingtime;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor(access = PROTECTED)
@Getter
public enum DayType {
    MON("월"),
    TUE("화"),
    WED("수"),
    THU("목"),
    FRI("금"),
    SAT("토"),
    SUN("일");

    private final String name;
}
