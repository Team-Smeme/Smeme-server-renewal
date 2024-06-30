package com.smeem.domain.diary.adapter;

import com.smeem.domain.diary.model.DeletedDiary;
import com.smeem.domain.persistence.entity.DiaryEntity;
import com.smeem.domain.diary.repository.DeletedDiaryRepository;
import com.smeem.domain.diary.repository.DiaryRepository;
import com.smeem.domain.persistence.entity.MemberEntity;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RepositoryAdapter
@RequiredArgsConstructor
public class DiaryDeleter {

    private final DiaryRepository diaryRepository;
    private final DeletedDiaryRepository deletedDiaryRepository;

    public void deleteAllByMember(final MemberEntity member) {
        diaryRepository.deleteAllByMember(member);
        deletedDiaryRepository.deleteByMember(member);
    }

    public void softDelete(final DiaryEntity diary) {
        deletedDiaryRepository.save(new DeletedDiary(diary));
        diary.deleteFromMember();
        diaryRepository.deleteById(diary.getId());
    }

    public void deleteByUpdatedAtBefore(final LocalDateTime dateTime) {
        deletedDiaryRepository.deleteByUpdatedAtBefore(dateTime);
    }
}
