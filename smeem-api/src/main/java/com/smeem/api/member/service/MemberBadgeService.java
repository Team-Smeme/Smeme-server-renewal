package com.smeem.api.member.service;


import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.member.adapter.memberbadge.MemberBadgeDeleter;
import com.smeem.domain.member.adapter.memberbadge.MemberBadgeFinder;
import com.smeem.domain.member.adapter.memberbadge.MemberBadgeSaver;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.MemberBadge;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberBadgeService {

    private final MemberBadgeFinder memberBadgeFinder;
    private final MemberBadgeSaver memberBadgeSaver;
    private final MemberBadgeDeleter memberBadgeDeleter;

    @Transactional
    public void deleteAllByMember(final Member member) {
        memberBadgeDeleter.deleteAllInBatch(member.getBadges());
    }

    @Transactional
    public void save(Member member, Badge badge) {
        val memberBadge = MemberBadge.builder()
                .member(member)
                .badge(badge)
                .build();
        memberBadgeSaver.save(memberBadge);
    }

    public Badge getBadgeByMemberId(final long memberId) {
        return memberBadgeFinder.findFirstByMemberIdOrderByCreatedAtDesc(memberId).getBadge();
    }
}
