package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.survey.CoachingSurvey;
import com.smeem.application.domain.survey.DissatisfactionType;
import com.smeem.common.util.GenericEnumListConverter;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "coaching_survey", schema = "smeem")
@NoArgsConstructor
public class CoachingSurveyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long diaryId;
    private Boolean isSatisfied;
    @Convert(converter = DissatisfactionTypeConverter.class)
    private List<DissatisfactionType> dissatisfactionTypes;
    private String reason;

    public CoachingSurveyEntity(CoachingSurvey coachingSurvey) {
        diaryId = coachingSurvey.getDiaryId();
        isSatisfied = coachingSurvey.isSatisfied();
        dissatisfactionTypes = coachingSurvey.getDissatisfactionTypes();
        reason = coachingSurvey.getReason();
    }

    @Converter(autoApply = true)
    public static class DissatisfactionTypeConverter extends GenericEnumListConverter<DissatisfactionType> {
        public DissatisfactionTypeConverter() {
            super(DissatisfactionType.class);
        }
    }
}
