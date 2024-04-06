package com.smeem.domain.member.repository;

import com.smeem.domain.member.model.MemberBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {

    List<MemberBadge> findAllByMemberId(final long memberId);
    Optional<MemberBadge> findFirstByMemberIdOrderByCreatedAtDesc(final long memberId);
}
