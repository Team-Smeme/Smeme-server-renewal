package com.smeem.application.port.input;

import com.smeem.application.port.input.dto.request.survey.CoachingSurveyRequest;

public interface SurveyUseCase {
    void saveCoachingSurveyResult(CoachingSurveyRequest request);
}
