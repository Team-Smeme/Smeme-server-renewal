package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.diary.Diary;
import com.smeem.application.port.output.persistence.DiaryPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.persistence.postgresql.persistence.entity.DeletedDiaryEntity;
import com.smeem.persistence.postgresql.persistence.entity.DiaryEntity;
import com.smeem.persistence.postgresql.persistence.repository.DeletedDiaryRepository;
import com.smeem.persistence.postgresql.persistence.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiaryAdapter implements DiaryPort {
    private final DiaryRepository diaryRepository;
    private final DeletedDiaryRepository deletedDiaryRepository;

    @Override
    public int countByMember(long memberId) {
        return diaryRepository.countByMemberId(memberId);
    }

    @Override
    public Diary save(Diary diary) {
        return diaryRepository.save(new DiaryEntity(diary)).toDomain();
    }

    @Override
    public boolean isExistByMemberAndYesterday(long memberId) {
        return diaryRepository.existsByMemberIdAndYesterday(memberId);
    }

    @Override
    public Diary findById(long id) {
        return find(id).toDomain();
    }

    @Override
    public Diary update(Diary diary) {
        val foundDiary = find(diary.getId());
        return foundDiary.update(diary).toDomain();
    }

    @Override
    public void softDelete(long diaryId) {
        deletedDiaryRepository.save(new DeletedDiaryEntity(find(diaryId)));
        diaryRepository.deleteById(diaryId);
    }

    @Override
    public List<Diary> findByMemberAndTerm(long memberId, LocalDate startAt, LocalDate endAt) {
        return diaryRepository.findByMemberIdBetween(memberId, startAt, endAt).stream()
                .map(DiaryEntity::toDomain)
                .toList();
    }

    @Override
    public boolean isExistByMemberAndPastAgo(long memberId, int days) {
        return diaryRepository.existsByMemberAndPastAgo(memberId, days);
    }

    @Override
    public int countWeeklyByMember(long memberId) {
        return (int) diaryRepository.countWeeklyByMemberId(memberId);
    }

    @Override
    public void deleteByCreatedAt(LocalDate expiredDate) {
        deletedDiaryRepository.deleteByCreatedAtBetween(
                expiredDate.atStartOfDay(),
                expiredDate.atStartOfDay().plusDays(1));
    }

    private DiaryEntity find(long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new SmeemException(ExceptionCode.NOT_FOUND, "(Diary ID: " + id + ")"));
    }
}
