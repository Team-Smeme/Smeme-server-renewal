package com.smeem.persistence.postgresql.persistence.entity;

import static lombok.AccessLevel.PROTECTED;

import com.smeem.application.domain.trainingtime.DayType;
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
    private DayType day;
    private int hour;
    private int minute;
    long memberId;
}
