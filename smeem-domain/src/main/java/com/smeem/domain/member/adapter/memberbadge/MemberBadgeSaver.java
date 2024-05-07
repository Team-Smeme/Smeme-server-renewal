package com.smeem.domain.member.adapter.memberbadge;

import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.MemberBadge;
import com.smeem.domain.member.repository.MemberBadgeRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RepositoryAdapter
@RequiredArgsConstructor
public class MemberBadgeSaver {

    private final MemberBadgeRepository memberBadgeRepository;

    public void save(MemberBadge memberBadge) {
        memberBadgeRepository.save(memberBadge);
    }

    public void saveByMemberAndBadge(Member member, Badge badge) {
        val memberBadge = new MemberBadge(member, badge);
        memberBadgeRepository.save(memberBadge);
    }
}
