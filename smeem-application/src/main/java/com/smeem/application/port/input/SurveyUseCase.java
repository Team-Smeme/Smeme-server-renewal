package com.smeem.application.port.input;

import com.smeem.application.domain.survey.CoachingSurvey;
import com.smeem.application.port.input.dto.request.survey.CoachingSurveyRequest;

public interface SurveyUseCase {
    CoachingSurvey saveCoachingSurveyResult(CoachingSurveyRequest request);
}
