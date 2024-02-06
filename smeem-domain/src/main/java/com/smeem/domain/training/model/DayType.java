package com.smeem.domain.training.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
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
