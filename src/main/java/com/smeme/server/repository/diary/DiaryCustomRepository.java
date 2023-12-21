package com.smeme.server.repository.diary;

import java.util.List;
import java.util.Optional;

import com.smeme.server.model.Diary;

public interface DiaryCustomRepository {
    Optional<Diary> findByIdFetchJoinCorrections(Long id);
    List<Diary> findDeleted();
}
