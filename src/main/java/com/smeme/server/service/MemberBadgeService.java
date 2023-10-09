package com.smeme.server.service;

import com.smeme.server.model.Member;
import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.MemberBadge;
import com.smeme.server.repository.badge.MemberBadgeRepository;
import com.smeme.server.util.message.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberBadgeService {

    private final MemberBadgeRepository memberBadgeRepository;

    public void deleteAllByMemberId(Long memberId) {
        memberBadgeRepository.deleteAllByMemberId(memberId);
    }

    protected void save(Member member, Badge badge) {
        MemberBadge memberBadge = MemberBadge.builder()
                        .member(member)
                        .badge(badge)
                        .build();
        memberBadgeRepository.save(memberBadge);
    }

    protected Badge getBadgeByMemberId(Long memberId) {
        return memberBadgeRepository.findFirstByMemberIdOrderByCreatedAtDesc(memberId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.EMPTY_BADGE.getMessage())).getBadge();
    }

    protected void deleteAllByMember(Member member) {
        memberBadgeRepository.deleteAll(member.getBadges());
    }
}
