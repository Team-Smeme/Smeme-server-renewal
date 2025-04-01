package com.smeem.application.port.input.dto.request.survey;

import com.smeem.application.domain.survey.CoachingSurvey;
import com.smeem.application.domain.survey.DissatisfactionType;

import java.util.List;

public record CoachingSurveyRequest(
        long diaryId,
        boolean isSatisfied,
        List<String> dissatisfactionTypes,
        String reason
) {

    public CoachingSurvey toDomain() {
        return CoachingSurvey.builder()
                .diaryId(diaryId)
                .isSatisfied(isSatisfied)
                .dissatisfactionTypes(DissatisfactionType.fromStringArray(dissatisfactionTypes))
                .reason(reason)
                .build();
    }
}
