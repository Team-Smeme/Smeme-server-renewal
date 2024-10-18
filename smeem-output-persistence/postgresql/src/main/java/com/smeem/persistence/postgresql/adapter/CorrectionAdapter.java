package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.diary.Correction;
import com.smeem.application.domain.diary.Diary;
import com.smeem.application.port.output.persistence.CorrectionPort;
import com.smeem.persistence.postgresql.persistence.entity.CorrectionEntity;
import com.smeem.persistence.postgresql.persistence.repository.diary.CorrectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CorrectionAdapter implements CorrectionPort {
    private final CorrectionRepository correctionRepository;

    @Override
    public List<Correction> saveAll(List<Correction> corrections, Diary diary) {
        UUID key = UUID.randomUUID();
        return correctionRepository.saveAll(
                corrections.stream().map(it -> new CorrectionEntity(it, diary, key)).toList()
        ).stream().map(CorrectionEntity::toDomain).toList();
    }

    @Override
    public int countDistinctByMemberAndDate(long memberId, LocalDate date) {
        return correctionRepository.countDistinctKeyByMemberIdAndCreatedAt(memberId, date);
    }

    @Override
    public List<Correction> findByDiary(long diaryId) {
        return correctionRepository.findByDiaryId(diaryId).stream().map(CorrectionEntity::toDomain).toList();
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
