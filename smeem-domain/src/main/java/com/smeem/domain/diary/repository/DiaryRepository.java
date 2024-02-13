package com.smeem.domain.diary.repository;

import com.smeem.domain.diary.model.Diary;
import com.smeem.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    void deleteAllByMember(Member member);
}
