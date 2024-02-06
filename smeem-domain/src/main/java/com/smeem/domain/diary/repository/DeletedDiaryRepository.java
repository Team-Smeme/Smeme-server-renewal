package com.smeem.domain.diary.repository;


import com.smeem.domain.diary.model.DeletedDiary;
import com.smeem.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface DeletedDiaryRepository extends JpaRepository<DeletedDiary, Long> {
    void deleteByUpdatedAtBefore(LocalDateTime expiredAt);
    void deleteByMember(Member member);
}
