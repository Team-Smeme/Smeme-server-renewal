package com.smeem.domain.goal.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long id;

    @Column(unique = true)
    @Enumerated(value = EnumType.STRING)
    private GoalType type;

    private String way;

    private String detail;

    @Builder
    public Goal(GoalType type, String way, String detail) {
        this.type = type;
        this.way = way;
        this.detail = detail;
    }
}
