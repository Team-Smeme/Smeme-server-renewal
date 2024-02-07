package com.smeem.api.member.service;


import com.smeem.common.exception.BadgeException;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.MemberBadge;
import com.smeem.domain.member.repository.MemberBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.smeem.common.code.failure.BadgeFailureCode.EMPTY_BADGE;

@Service
@RequiredArgsConstructor
public class MemberBadgeService {

    private final MemberBadgeRepository memberBadgeRepository;

    protected void save(Member member, Badge badge) {
        MemberBadge memberBadge = MemberBadge.builder()
                .member(member)
                .badge(badge)
                .build();
        memberBadgeRepository.save(memberBadge);
    }

    public void deleteAllByMember(Member member) {
        memberBadgeRepository.deleteAll(member.getBadges());
    }

    protected Badge getBadgeByMemberId(Long memberId) {
        return memberBadgeRepository.findFirstByMemberIdOrderByCreatedAtDesc(memberId).orElseThrow(
                () -> new BadgeException(EMPTY_BADGE)).getBadge();
    }
}