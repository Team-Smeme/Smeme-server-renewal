package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.persistence.postgresql.persistence.entity.MemberBadgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberBadgeRepository extends JpaRepository<MemberBadgeEntity, Long> {
    List<MemberBadgeEntity> findByMemberId(long memberId);
    int countByMemberId(long memberId);
    Optional<MemberBadgeEntity> findFirstByMemberId(long memberId);
}
