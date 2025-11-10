package com.smeem.application.domain.diary;

import com.smeem.application.config.SmeemProperties;
import com.smeem.application.port.input.CorrectionUseCase;
import com.smeem.application.port.input.dto.response.diary.CorrectionsResponse;
import com.smeem.application.port.output.persistence.CorrectionPort;
import com.smeem.application.port.output.persistence.DiaryPort;
import com.smeem.application.port.output.web.openai.OpenAiPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorrectionService implements CorrectionUseCase {

    private final CorrectionPort correctionPort;
    private final DiaryPort diaryPort;
    private final OpenAiPort openAiPort;
    private final SmeemProperties smeemProperties;

    @Transactional
    public CorrectionsResponse correctDiary(long memberId, long diaryId) {
        LocalDate today = LocalDate.now();

        // 제한 횟수 검증
        int correctionCount = correctionPort.countDistinctByMemberAndDate(memberId, today);
        smeemProperties.getLimit().validateCorrectionLimit(correctionCount);

        // 일기 소유권 검증
        Diary diary = diaryPort.findById(diaryId);
        validateDiaryOwnership(diary.getMemberId(), memberId);

        List<Correction> corrections = createCorrections(diary);
        return CorrectionsResponse.of(corrections);
    }

    private List<Correction> createCorrections(Diary diary) {
        List<Correction> corrections = openAiPort.promptCorrections(diary.getContent());
        return correctionPort.save(corrections, diary);
    }

    private void validateDiaryOwnership(long diaryWriterId, long memberId) {
        if (diaryWriterId != memberId) {
            throw new SmeemException(ExceptionCode.INVALID_MEMBER_AND_DIARY);
        }
    }
}
