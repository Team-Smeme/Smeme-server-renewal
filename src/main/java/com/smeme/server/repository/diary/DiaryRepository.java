package com.smeme.server.repository.diary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.model.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryCustomRepository {
}
