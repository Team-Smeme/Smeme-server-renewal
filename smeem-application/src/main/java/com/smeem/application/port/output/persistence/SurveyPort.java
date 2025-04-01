package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.survey.CoachingSurvey;

public interface SurveyPort {
    CoachingSurvey saveCoachingSurveyResult(CoachingSurvey coachingSurvey);
}