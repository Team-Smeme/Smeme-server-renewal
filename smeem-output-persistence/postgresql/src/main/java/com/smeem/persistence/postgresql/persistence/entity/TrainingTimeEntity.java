package com.smeem.persistence.postgresql.persistence.entity;

import static lombok.AccessLevel.PROTECTED;

import com.smeem.application.domain.trainingtime.DayType;
import com.smeem.application.domain.trainingtime.TrainingTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class TrainingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private DayType dayType;
    private int hour;
    private int minute;
    long memberId;

    public TrainingTimeEntity(TrainingTime trainingTime) {
        this.dayType = trainingTime.getDayType();
        this.hour = trainingTime.getHour();
        this.minute = trainingTime.getMinute();
    }

    public TrainingTime toDomain() {
        return TrainingTime.builder()
                .id(id)
                .dayType(dayType)
                .hour(hour)
                .minute(minute)
                .memberId(memberId)
                .build();
    }
}
