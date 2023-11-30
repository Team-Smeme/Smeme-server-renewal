package com.smeme.server.repository.diary;

import com.smeme.server.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.model.Diary;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryCustomRepository {

    void deleteAllByMember(Member member);

    List<Diary> findAllByMemberId(Long memberId);
}
