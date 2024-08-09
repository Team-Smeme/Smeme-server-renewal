package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.goal.Goal;
import com.smeem.application.domain.goal.GoalType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "goal", schema = "smeem")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder
public class GoalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type", nullable = false, unique = true)
    @Enumerated(value = EnumType.STRING)
    private GoalType goalType;
    private String way;
    private String detail;

    public Goal toDomain() {
        return Goal.builder()
                .id(id)
                .goalType(goalType)
                .way(way)
                .detail(detail)
                .build();
    }
}
