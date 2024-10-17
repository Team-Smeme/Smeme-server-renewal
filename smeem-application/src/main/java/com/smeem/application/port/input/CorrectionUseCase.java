package com.smeem.application.port.input;

import com.smeem.application.port.input.dto.response.diary.CorrectionsResponse;

public interface CorrectionUseCase {
    CorrectionsResponse correctDiary(long memberId, long diaryId);
}
