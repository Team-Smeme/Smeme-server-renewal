package com.smeem.api.member.service;


import com.smeem.common.exception.BadgeException;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.MemberBadge;
import com.smeem.domain.member.repository.MemberBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.smeem.common.code.failure.BadgeFailureCode.EMPTY_BADGE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberBadgeService {

    private final MemberBadgeRepository memberBadgeRepository;

    @Transactional
    public void deleteAllByMember(Member member) {
        memberBadgeRepository.deleteAllInBatch(member.getBadges());
    }

    @Transactional
    public void save(Member member, Badge badge) {
        MemberBadge memberBadge = MemberBadge.builder()
                .member(member)
                .badge(badge)
                .build();
        memberBadgeRepository.save(memberBadge);
    }

    public Badge getBadgeByMemberId(Long memberId) {
        return memberBadgeRepository.findFirstByMemberIdOrderByCreatedAtDesc(memberId).orElseThrow(
                () -> new BadgeException(EMPTY_BADGE)).getBadge();
    }
}
