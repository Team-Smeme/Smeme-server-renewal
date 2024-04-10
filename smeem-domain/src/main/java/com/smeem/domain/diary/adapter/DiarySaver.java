package com.smeem.domain.diary.adapter;

import com.smeem.domain.diary.model.Diary;
import com.smeem.domain.diary.repository.DiaryRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class DiarySaver {

    private final DiaryRepository diaryRepository;

    public Diary save(Diary diary) {
        return diaryRepository.save(diary);
    }
}
