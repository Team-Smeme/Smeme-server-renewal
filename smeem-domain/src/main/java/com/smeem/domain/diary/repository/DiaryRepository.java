package com.smeem.domain.diary.repository;

import com.smeem.domain.persistence.entity.DiaryEntity;
import com.smeem.domain.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    void deleteAllByMember(MemberEntity member);
    Optional<DiaryEntity> findFirstByMemberOrderByCreatedAtDesc(MemberEntity member);
}
