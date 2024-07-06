package com.smeem.application.domain.trainingtime;

import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor(access = PROTECTED)
@Getter
public enum DayType {
    MON("월", 1),
    TUE("화", 2),
    WED("수", 3),
    THU("목", 4),
    FRI("금", 5),
    SAT("토", 6),
    SUN("일", 7);

    private final String name;
    private final int value;

    public static DayType fromValue(int value) {
        for (DayType dayType : DayType.values()) {
            if (dayType.value == value) {
                return dayType;
            }
        }
        throw new SmeemException(ExceptionCode.NOT_FOUND, "DayType Value : " + value);
    }
}
