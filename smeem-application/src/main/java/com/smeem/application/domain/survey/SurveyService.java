package com.smeem.application.domain.survey;

import com.smeem.application.port.input.SurveyUseCase;
import com.smeem.application.port.input.dto.request.survey.CoachingSurveyRequest;
import com.smeem.application.port.output.persistence.SurveyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService implements SurveyUseCase {
    private final SurveyPort surveyPort;

    public void saveCoachingSurveyResult(CoachingSurveyRequest request) {
        surveyPort.saveCoachingSurveyResult(request.toDomain());
    }
}
