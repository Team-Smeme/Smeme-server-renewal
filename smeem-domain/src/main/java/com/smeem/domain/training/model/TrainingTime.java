package com.smeem.domain.training.model;

import static java.util.Objects.*;


import com.smeem.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class TrainingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private DayType day;

    // TODO : validation -> 생성자에 validation method 직접 구현
    // @Min(value = 1, message = "시(hour)는 1 이상이어야 합니다.")
    // @Max(value = 24, message = "시(hour)는 24 이하여야 합니다.")
    private int hour;

    // TODO : validation -> 생성자에 validation method 직접 구현
    // @Min(value = 0, message = "분(minute)은 0 이상이어야 합니다.")
    // @Max(value = 59, message = "분(minute)은 59 이하이어야 합니다.")
    private int minute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public TrainingTime(DayType day, int hour, int minute, Member member) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        setMember(member);
    }

    private void setMember(Member member) {
        if (nonNull(this.member)) {
            this.member.getTrainingTimes().remove(this);
        }
        this.member = member;
        member.getTrainingTimes().add(this);
    }
}