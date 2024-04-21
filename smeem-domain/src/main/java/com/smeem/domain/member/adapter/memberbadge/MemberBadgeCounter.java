package com.smeem.domain.member.adapter.memberbadge;


import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.member.repository.MemberBadgeRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class MemberBadgeCounter {

    private final MemberBadgeRepository memberBadgeRepository;

    public long countByBadge(final Badge badge) {
        return memberBadgeRepository.countByBadge(badge);
    }

    public long countByBadgeIdAndMemberId(final long badgeId, final long memberId) {
        return memberBadgeRepository.countByBadgeIdAndMemberId(badgeId, memberId);
    }
}
