package com.smeem.application.domain.survey;

import com.smeem.application.port.input.SurveyUseCase;
import com.smeem.application.port.input.dto.request.survey.CoachingSurveyRequest;
import com.smeem.application.port.output.persistence.DiaryPort;
import com.smeem.application.port.output.persistence.SurveyPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.common.logger.HookLogger;
import com.smeem.common.logger.LoggingMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService implements SurveyUseCase {
    private final SurveyPort surveyPort;
    private final DiaryPort diaryPort;
    private final HookLogger hookLogger;

    @Transactional
    public CoachingSurvey saveCoachingSurveyResult(CoachingSurveyRequest request) {
        validateDiaryExists(request.diaryId());
        validateDissatisfactionType(request.isSatisfied(), request.dissatisfactionTypes());

        CoachingSurvey coachingSurveyResult = request.toDomain();

        hookLogger.send(LoggingMessage.coachingSurvey(
                coachingSurveyResult.isSatisfied(),
                coachingSurveyResult.getDissatisfactionTypes().stream()
                        .map(DissatisfactionType::getDescription)
                        .collect(Collectors.joining(", ")),
                coachingSurveyResult.getReason()));
        return surveyPort.saveCoachingSurveyResult(coachingSurveyResult);
    }

    private void validateDiaryExists(long diaryId) {
        if (!diaryPort.existsById(diaryId)) {
            throw new SmeemException(ExceptionCode.NOT_FOUND, "(Diary ID: " + diaryId + ")");
        }
    }

    private void validateDissatisfactionType(boolean isSatisfied, List<String> dissatisfactionTypes) {
        if (isSatisfied && (dissatisfactionTypes != null && !dissatisfactionTypes.isEmpty())) {
            throw new SmeemException(ExceptionCode.INVALID_FIELD, "불만족을 선택한 경우에만 유형을 선택할 수 있습니다.");
        }
    }
}
