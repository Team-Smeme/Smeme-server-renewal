package com.smeem.domain.member.adapter;

import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.MemberBadge;
import com.smeem.domain.member.repository.MemberBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberBadgeSaver {

    private final MemberBadgeRepository memberBadgeRepository;

    public void save(MemberBadge memberBadge) {
        memberBadgeRepository.save(memberBadge);
    }
}
