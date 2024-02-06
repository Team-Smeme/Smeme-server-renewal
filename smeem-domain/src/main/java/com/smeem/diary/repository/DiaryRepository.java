package com.smeem.diary.repository;

import com.smeme.server.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.model.Diary;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    void deleteAllByMember(Member member);

    List<Diary> findAllByMemberId(Long memberId);
}
