package com.smeme.server.model.goal;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Goal {

    @Id
    @GeneratedValue(strategy = IDENTITY)
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
