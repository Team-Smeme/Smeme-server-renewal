package com.smeme.server.repository.diary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.model.Diary;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryCustomRepository {

    void deleteAllByMemberId(Long id);

    List<Diary> findAllByMemberId(Long memberId);
}
