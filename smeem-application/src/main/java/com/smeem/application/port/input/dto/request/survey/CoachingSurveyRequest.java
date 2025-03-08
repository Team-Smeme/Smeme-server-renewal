package com.smeem.application.port.input.dto.request.survey;

import com.smeem.application.domain.survey.DissatisfactionReason;

public record CoachingSurveyRequest(
        Long diaryId,
        boolean satisfaction,
        DissatisfactionReason reason,
        String detailReason
) {
}
