package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.survey.CoachingSurvey;
import com.smeem.application.port.output.persistence.SurveyPort;
import com.smeem.persistence.postgresql.persistence.entity.CoachingSurveyEntity;
import com.smeem.persistence.postgresql.persistence.repository.CoachingSurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SurveyAdapter implements SurveyPort {
    private final CoachingSurveyRepository surveyRepository;

    @Override
    public CoachingSurvey saveCoachingSurveyResult(CoachingSurvey coachingSurvey) {
        CoachingSurveyEntity savedEntity = surveyRepository.save(new CoachingSurveyEntity(coachingSurvey));

        return CoachingSurvey.builder()
                .diaryId(savedEntity.getDiaryId())
                .isSatisfied(savedEntity.getIsSatisfied())
                .dissatisfactionTypes(savedEntity.getDissatisfactionTypes())
                .reason(savedEntity.getReason())
                .build();
    }
}