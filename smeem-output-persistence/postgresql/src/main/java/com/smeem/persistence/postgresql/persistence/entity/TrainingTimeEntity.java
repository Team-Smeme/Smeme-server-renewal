package com.smeem.persistence.postgresql.persistence.entity;

import static lombok.AccessLevel.PROTECTED;

import com.smeem.application.domain.trainingtime.DayType;
import com.smeem.application.domain.trainingtime.TrainingTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "training_time", schema = "smeem")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class TrainingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private DayType dayType;
    @Column(nullable = false)
    private int hour;
    @Column(nullable = false)
    private int minute;
    @Column(nullable = false)
    long memberId;

    public TrainingTimeEntity(TrainingTime trainingTime) {
        this.dayType = trainingTime.getDayType();
        this.hour = trainingTime.getHour();
        this.minute = trainingTime.getMinute();
        this.memberId = trainingTime.getMemberId();
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
