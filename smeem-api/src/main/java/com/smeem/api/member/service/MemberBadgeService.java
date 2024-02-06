package com.smeem.api.member.service;


import com.smeem.common.code.ErrorMessage;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.MemberBadge;
import com.smeem.domain.member.repository.MemberBadgeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                () -> new EntityNotFoundException(ErrorMessage.EMPTY_BADGE.getMessage())).getBadge();
    }
}
