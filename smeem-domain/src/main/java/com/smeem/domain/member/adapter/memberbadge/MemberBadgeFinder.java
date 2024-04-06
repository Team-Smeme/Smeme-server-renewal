package com.smeem.domain.member.adapter.memberbadge;

import com.smeem.common.code.failure.MemberFailureCode;
import com.smeem.domain.member.exception.MemberException;
import com.smeem.domain.member.model.MemberBadge;
import com.smeem.domain.member.repository.MemberBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberBadgeFinder {

    private final MemberBadgeRepository memberBadgeRepository;

    public List<MemberBadge> findAllByMemberId(final long id) {
        return memberBadgeRepository.findAllByMemberId(id);
    }

    public MemberBadge findFirstByMemberIdOrderByCreatedAtDesc(final long id) {
        return memberBadgeRepository.findFirstByMemberIdOrderByCreatedAtDesc(id)
                .orElseThrow(() -> new MemberException(MemberFailureCode.EMPTY_MEMBER));
    }
}
