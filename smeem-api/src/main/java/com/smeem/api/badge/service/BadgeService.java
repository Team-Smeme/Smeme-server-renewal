package com.smeem.api.badge.service;

import com.smeem.api.badge.controller.dto.response.BadgeListResponse;
import com.smeem.common.exception.BadgeException;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.model.BadgeType;
import com.smeem.domain.badge.repository.BadgeRepository;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.MemberBadge;
import com.smeem.domain.member.repository.MemberBadgeRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.smeem.common.code.failure.BadgeFailureCode.INVALID_BADGE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeService {

    private final MemberBadgeRepository memberBadgeRepository;
    private final BadgeRepository badgeRepository;

    @Transactional
    public void saveMemberBadge(Member member, Badge badge) {
        memberBadgeRepository.save(MemberBadge.builder()
                        .member(member)
                        .badge(badge)
                        .build());
    }
    public BadgeListResponse getBadges(final long memberId) {
        val badges = badgeRepository.findAllOrderById();
        val badgeMap = classifiedByType(badges);
        val memberBadges = memberBadgeRepository.findAllByMemberId(memberId);
        return BadgeListResponse.of(badgeMap, memberBadges);
    }

    public Badge get(Long id) {
        return badgeRepository.findById(id)
                .orElseThrow(() -> new BadgeException(INVALID_BADGE));
    }

    private Map<BadgeType, List<Badge>> classifiedByType(List<Badge> badges) {
        val badgeMap = new HashMap<BadgeType, List<Badge>>();
        badges.forEach(badge -> putBadgeMap(badgeMap, badge));
        return badgeMap;
    }

    private void putBadgeMap(Map<BadgeType, List<Badge>> badgeMap, Badge badge) {
        badgeMap.computeIfAbsent(badge.getType(), k -> new ArrayList<>()).add(badge);
    }

}
