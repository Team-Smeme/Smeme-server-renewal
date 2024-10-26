package com.smeem.output.persistence.mongodb.adapter;

import com.smeem.application.domain.diary.Correction;
import com.smeem.application.domain.diary.Diary;
import com.smeem.application.port.output.persistence.CorrectionPort;
import com.smeem.output.persistence.mongodb.persistence.document.CorrectionDocument;
import com.smeem.output.persistence.mongodb.persistence.repository.CorrectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CorrectionAdapter implements CorrectionPort {
    private final CorrectionRepository correctionRepository;

    @Override
    public List<Correction> save(List<Correction> corrections, Diary diary) {
        CorrectionDocument savedCorrection = correctionRepository.save(new CorrectionDocument(corrections, diary));
        return savedCorrection.toDomain();
    }

    @Override
    public int countDistinctByMemberAndDate(long memberId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        return correctionRepository.countByMemberIdAndCreatedAtBetween(memberId, startOfDay, endOfDay);
    }

    @Override
    public List<Correction> findByDiary(long diaryId) {
        return correctionRepository.findByDiaryId(diaryId)
                .map(CorrectionDocument::toDomain)
                .orElseGet(ArrayList::new);
    }

    @Override
    public void deleteByDiary(long diaryId) {
        correctionRepository.deleteByDiaryId(diaryId);
    }

    @Override
    public void deleteByMember(long memberId) {
        correctionRepository.deleteByMemberId(memberId);
    }
}
