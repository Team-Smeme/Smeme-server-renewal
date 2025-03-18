package com.smeem.application.port.input.dto.request.survey;

import com.smeem.application.domain.survey.CoachingSurvey;
import com.smeem.application.domain.survey.DissatisfactionType;

import java.util.List;

public record CoachingSurveyRequest(
        Long diaryId,
        boolean isSatisfied,
        List<DissatisfactionType> dissatisfactionTypes,
        String reason
) {

    public CoachingSurvey toDomain() {
        return CoachingSurvey.builder()
                .diaryId(diaryId)
                .isSatisfied(isSatisfied)
                .dissatisfactionTypes(dissatisfactionTypes)
                .reason(reason)
                .build();
    }
}
