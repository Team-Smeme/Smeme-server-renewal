package com.smeem.application.domain.diary;

import com.smeem.application.config.SmeemProperties;
import com.smeem.application.domain.member.Member;
import com.smeem.application.port.input.CorrectionUseCase;
import com.smeem.application.port.input.dto.response.diary.CorrectionsResponse;
import com.smeem.application.port.output.cache.CachePort;
import com.smeem.application.port.output.persistence.CorrectionPort;
import com.smeem.application.port.output.persistence.DiaryPort;
import com.smeem.application.port.output.persistence.MemberPort;
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
    private final MemberPort memberPort;

    @Transactional
    public CorrectionsResponse correctDiary(long memberId, long diaryId) {
        LocalDate today = LocalDate.now();
        String key = getCorrectionCacheKey(memberId, today);

        // 제한 횟수 검증
        int correctionCount = getOrUpdateCorrectionCount(key, memberId, today);
        smeemProperties.getLimit().validateCorrectionLimit(correctionCount);

        // 일기 소유권 검증
        Diary diary = diaryPort.findById(diaryId);
        diary.validateDiaryOwnership(memberId);

        // AI 첨삭 및 캐시 업데이트
        List<Correction> corrections = createCorrections(diary);
        cachePort.incrementInt(key);

        Member member = memberPort.findById(memberId);
        int totalCorrectionCount = correctionPort.countByMember(memberId);
        return CorrectionsResponse.of(corrections, member, totalCorrectionCount);
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
        return correctionPort.save(corrections, diary);
    }
}
