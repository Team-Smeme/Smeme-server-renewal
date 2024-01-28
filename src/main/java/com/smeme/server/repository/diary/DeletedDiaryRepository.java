package com.smeme.server.repository.diary;

import com.smeme.server.model.DeletedDiary;
import com.smeme.server.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface DeletedDiaryRepository extends JpaRepository<DeletedDiary, Long> {
    void deleteByUpdatedAtBefore(LocalDateTime expiredAt);
    void deleteByMember(Member member);
}
