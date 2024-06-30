package com.smeem.domain.member.adapter.memberbadge;

import com.smeem.domain.persistence.entity.BadgeEntity;
import com.smeem.domain.persistence.entity.MemberEntity;
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

    public void saveByMemberAndBadge(MemberEntity member, BadgeEntity badge) {
        val memberBadge = new MemberBadge(member, badge);
        memberBadgeRepository.save(memberBadge);
    }
}
