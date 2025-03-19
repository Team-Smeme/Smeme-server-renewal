package com.smeem.application.domain.survey;

import com.smeem.application.port.input.SurveyUseCase;
import com.smeem.application.port.input.dto.request.survey.CoachingSurveyRequest;
import com.smeem.application.port.output.persistence.DiaryPort;
import com.smeem.application.port.output.persistence.SurveyPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyService implements SurveyUseCase {
    private final SurveyPort surveyPort;
    private final DiaryPort diaryPort;

    public CoachingSurvey saveCoachingSurveyResult(CoachingSurveyRequest request) {
        validateCoachingSurveyRequest(request);
        return surveyPort.saveCoachingSurveyResult(request.toDomain());
    }

    private void validateCoachingSurveyRequest(CoachingSurveyRequest request) {
        if (request.diaryId() == null) {
            throw new SmeemException(ExceptionCode.MISSING_FIELD, "diaryId는 필수 값입니다.");
        }
        if (request.isSatisfied() == null) {
            throw new SmeemException(ExceptionCode.MISSING_FIELD, "isSatisfied는 필수 값입니다.");
        }

        diaryPort.findById(request.diaryId());

        if (request.isSatisfied() && (request.dissatisfactionTypes() != null && !request.dissatisfactionTypes().isEmpty())) {
            throw new SmeemException(ExceptionCode.INVALID_FIELD, "불만족을 선택한 경우에만 유형을 선택할 수 있습니다.");
        }
    }
}
