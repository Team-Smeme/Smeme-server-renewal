package com.smeem.domain.member.adapter.memberbadge;

import com.smeem.domain.member.model.MemberBadge;
import com.smeem.domain.member.repository.MemberBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberBadgeDeleter {
    private final MemberBadgeRepository memberBadgeRepository;

    public void deleteAllInBatch(List<MemberBadge> memberBadges) {
        memberBadgeRepository.deleteAllInBatch(memberBadges);
    }
}
