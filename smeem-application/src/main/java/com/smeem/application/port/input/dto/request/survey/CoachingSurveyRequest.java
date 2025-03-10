package com.smeem.application.port.input.dto.request.survey;

import com.smeem.application.domain.survey.CoachingSurvey;
import com.smeem.application.domain.survey.DissatisfactionType;

public record CoachingSurveyRequest(
        Long diaryId,
        boolean isSatisfied,
        DissatisfactionType dissatisfactionType,
        String reason
) {

    public CoachingSurvey toDomain() {
        return CoachingSurvey.builder()
                .diaryId(diaryId)
                .isSatisfied(isSatisfied)
                .dissatisfactionType(dissatisfactionType)
                .reason(reason)
                .build();
    }
}
