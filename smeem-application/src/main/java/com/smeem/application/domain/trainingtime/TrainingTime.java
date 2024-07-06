package com.smeem.application.domain.trainingtime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TrainingTime {
    Long id;
    DayType dayType;
    int hour;
    int minute;
    long memberId;

    public static TrainingTime of(DayType dayType, int hour, int minute, long memberId) {
        return TrainingTime.builder()
                .dayType(dayType)
                .hour(hour)
                .minute(minute)
                .memberId(memberId)
                .build();
    }
}
