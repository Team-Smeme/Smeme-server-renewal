package com.smeem.application.domain.diary;

import com.smeem.application.config.SmeemProperties;
import com.smeem.application.port.input.CorrectionUseCase;
import com.smeem.application.port.input.dto.response.diary.CorrectionsResponse;
import com.smeem.application.port.output.cache.CachePort;
import com.smeem.application.port.output.persistence.CorrectionPort;
import com.smeem.application.port.output.persistence.DiaryPort;
import com.smeem.application.port.output.web.openai.OpenAiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorrectionService implements CorrectionUseCase {
    private final CachePort cachePort;
    private final CorrectionPort correctionPort;
    private final DiaryPort diaryPort;
    private final OpenAiPort openAiPort;
    private final SmeemProperties smeemProperties;

    @Transactional
    public CorrectionsResponse correctDiary(long memberId, long diaryId) {
        LocalDate today = LocalDate.now();
        String key = getCorrectionCacheKey(memberId, today);

        int correctionCount = getOrUpdateCorrectionCount(key, memberId, today);
        smeemProperties.getLimit().validateCorrectionLimit(correctionCount);

        Diary diary = diaryPort.findById(diaryId);
        diary.validateDiaryOwnership(memberId);

        List<Correction> corrections = createCorrections(diary);
        cachePort.incrementInt(key);

        return CorrectionsResponse.of(corrections);
    }

    private String getCorrectionCacheKey(long memberId, LocalDate date) {
        String today = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return "correction:" + today + ":" + memberId;
    }

    private int getOrUpdateCorrectionCount(String key, long memberId, LocalDate date) {
        return cachePort.getInt(key)
                .orElseGet(() -> updateCacheWithCorrectionCount(key, memberId, date));
    }

    private int updateCacheWithCorrectionCount(String key, long memberId, LocalDate date) {
        int count = correctionPort.countDistinctByMemberAndDate(memberId, date);
        cachePort.setInt(key, count);
        return count;
    }

    private List<Correction> createCorrections(Diary diary) {
        List<Correction> corrections = openAiPort.promptCorrections(diary.getContent());
        return correctionPort.saveAll(corrections, diary);
    }
}
