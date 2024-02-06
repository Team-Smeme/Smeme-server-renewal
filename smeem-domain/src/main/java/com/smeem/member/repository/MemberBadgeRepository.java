package com.smeem.member.repository;
import com.smeme.server.model.badge.MemberBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {

    List<MemberBadge> findAllByMemberId(Long memberId);
    Optional<MemberBadge> findFirstByMemberIdOrderByCreatedAtDesc(Long memberId);
}
