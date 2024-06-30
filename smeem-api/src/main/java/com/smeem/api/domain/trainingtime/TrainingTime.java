package com.smeem.api.domain.trainingtime;

public record TrainingTime(
        DayType day,
        int hour,
        int minute
) {
}
