package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.survey.CoachingSurvey;
import com.smeem.application.domain.survey.DissatisfactionType;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coaching_survey", schema = "smeem")
@NoArgsConstructor
public class CoachingSurveyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long diaryId;
    private Boolean isSatisfied;
    @Enumerated(EnumType.STRING)
    private DissatisfactionType dissatisfactionType;
    private String reason;

    public CoachingSurveyEntity(CoachingSurvey coachingSurvey) {
        diaryId = coachingSurvey.getDiaryId();
        isSatisfied = coachingSurvey.isSatisfied();
        dissatisfactionType = coachingSurvey.getDissatisfactionType();
        reason = coachingSurvey.getReason();
    }
}
