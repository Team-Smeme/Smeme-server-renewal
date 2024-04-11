package com.smeem.api.badge.service;

import com.smeem.api.badge.service.dto.response.BadgeListServiceResponse;
import com.smeem.domain.badge.adapter.BadgeFinder;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.model.BadgeType;
import com.smeem.domain.member.adapter.memberbadge.MemberBadgeFinder;
import com.smeem.domain.member.adapter.memberbadge.MemberBadgeSaver;
import com.smeem.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeService {

    private final BadgeFinder badgeFinder;
    private final MemberBadgeSaver memberBadgeSaver;
    private final MemberBadgeFinder memberBadgeFinder;

    public BadgeListServiceResponse getBadges(final long memberId) {
        val badges = badgeFinder.findAllOrderById();
        val badgeMap = classifiedByType(badges);
        val memberBadges = memberBadgeFinder.findAllByMemberId(memberId);
        return BadgeListServiceResponse.of(badgeMap, memberBadges);
    }

    public Optional<Badge> createCountingMemberBadge(final Member member) {
        val countingBadge = badgeFinder.findBadgeByDiaryCount(member.getDiaries().size());
        return createMemberBadge(member, countingBadge);
    }

    public Optional<Badge> createComboMemberBadge(final Member member) {
        val comboBadge = badgeFinder.findBadgeByDiaryComboCount(member.getDiaryComboInfo().getDiaryComboCount());
        return createMemberBadge(member, comboBadge);
    }

    private Optional<Badge> createMemberBadge(final Member member, final Optional<Badge> badge) {
        if (badge.isPresent() && member.hasNotBadge(badge.get())) {
            memberBadgeSaver.saveByMemberAndBadge(member, badge.get());
            return badge;
        }
        return Optional.empty();
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
