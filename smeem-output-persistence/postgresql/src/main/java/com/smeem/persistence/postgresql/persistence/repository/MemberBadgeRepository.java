package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.persistence.postgresql.persistence.entity.MemberBadgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberBadgeRepository extends JpaRepository<MemberBadgeEntity, Long> {
    List<MemberBadgeEntity> findByMemberId(long memberId);
}
