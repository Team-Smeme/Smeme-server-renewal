package com.smeem.domain.member.adapter.memberbadge;

import com.smeem.domain.member.model.MemberBadge;
import com.smeem.domain.member.repository.MemberBadgeRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RepositoryAdapter
@RequiredArgsConstructor
public class MemberBadgeDeleter {
    private final MemberBadgeRepository memberBadgeRepository;

    public void deleteAllInBatch(List<MemberBadge> memberBadges) {
        memberBadgeRepository.deleteAllInBatch(memberBadges);
    }
}
