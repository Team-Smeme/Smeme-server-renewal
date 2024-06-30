package com.smeem.domain.member.adapter.memberbadge;

import com.smeem.domain.persistence.entity.BadgeEntity;
import com.smeem.domain.member.repository.MemberBadgeRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class MemberBadgeCounter {

    private final MemberBadgeRepository memberBadgeRepository;

    public long countByBadge(final BadgeEntity badge) {
        return memberBadgeRepository.countByBadge(badge);
    }
}
