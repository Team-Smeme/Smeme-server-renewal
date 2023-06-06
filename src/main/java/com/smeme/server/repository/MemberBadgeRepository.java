package com.smeme.server.repository;

import com.smeme.server.model.Member;
import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.MemberBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {

    List<MemberBadge> findAllByMemberId(Long memberId);
    boolean existsByMemberAndBadge(Member member, Badge badge);
}
