package com.smeem.domain.training.model;

import static com.smeem.common.code.failure.TrainingTimeFailureCode.INVALID_HOUR;
import static com.smeem.common.code.failure.TrainingTimeFailureCode.INVALID_MINUTE;
import static java.util.Objects.*;
import static lombok.AccessLevel.PROTECTED;

import com.smeem.common.exception.TrainingTimeException;
import com.smeem.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class TrainingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private DayType day;

    private int hour;

    private int minute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public TrainingTime(DayType day, int hour, int minute, Member member) {
        this.day = day;
        setHour(hour);
        setMinute(minute);
        setMember(member);
    }

    private void setMember(Member member) {
        if (nonNull(this.member)) {
            this.member.getTrainingTimes().remove(this);
        }
        this.member = member;
        member.getTrainingTimes().add(this);
    }

    private void setHour(int hour) {
        if (hour < 1 || hour > 24) {
            throw new TrainingTimeException(INVALID_HOUR);
        }
        this.hour = hour;
    }

    private void setMinute(int minute) {
        if (minute != 0 && minute != 30) {
            throw new TrainingTimeException(INVALID_MINUTE);
        }
        this.minute = minute;
    }
}