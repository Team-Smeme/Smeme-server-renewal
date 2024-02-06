package com.smeem.domain.goal.model;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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
