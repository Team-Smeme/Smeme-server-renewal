package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.plan.Plan;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class PlanEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String content;
    private int clearCount;

    public Plan toDomain() {
        return Plan.builder()
                .id(id)
                .content(content)
                .clearCount(clearCount)
                .build();
    }
}
