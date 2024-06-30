package com.smeem.domain.diary.adapter;

import com.smeem.domain.diary.exception.DiaryException;
import com.smeem.domain.persistence.entity.DiaryEntity;
import com.smeem.domain.diary.repository.DiaryRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

import static com.smeem.common.code.failure.DiaryFailureCode.INVALID_DIARY;

@RepositoryAdapter
@RequiredArgsConstructor
public class DiaryFinder {

    private final DiaryRepository diaryRepository;

    public DiaryEntity findById(final long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new DiaryException(INVALID_DIARY));
    }
}
