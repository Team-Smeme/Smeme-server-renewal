package com.smeem.api.badge.service;

import com.smeem.api.badge.service.dto.response.BadgeListServiceResponse;
import com.smeem.domain.badge.adapter.BadgeFinder;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.model.BadgeType;
import com.smeem.domain.member.adapter.MemberBadgeFinder;
import com.smeem.domain.member.adapter.MemberBadgeSaver;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.MemberBadge;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeService {

    private final BadgeFinder badgeFinder;
    private final MemberBadgeSaver memberBadgeSaver;
    private final MemberBadgeFinder memberBadgeFinder;

    @Transactional
    public void saveMemberBadge(final Member member, final Badge badge) {
        val memberBadge = MemberBadge.builder()
                .member(member)
                .badge(badge)
                .build();
        memberBadgeSaver.save(memberBadge);
    }
    public BadgeListServiceResponse getBadges(final long memberId) {
        val badges = badgeFinder.findAllOrderById();
        val badgeMap = classifiedByType(badges);
        val memberBadges = memberBadgeFinder.findAllByMemberId(memberId);
        return BadgeListServiceResponse.of(badgeMap, memberBadges);
    }

    public Badge getBadgeByCountOfDiary(final int diaryCount) {
        return switch (diaryCount) {
            case 50 -> badgeFinder.findById(5L);
            case 30 -> badgeFinder.findById(4L);
            case 10 -> badgeFinder.findById(3L);
            case 1 -> badgeFinder.findById(2L);
            default -> null;
        };
    }

    public Badge getBadgeByComboCountOfDiary(final int diaryComboCount) {
        return switch (diaryComboCount) {
            case 30 -> badgeFinder.findById(9L);
            case 15 -> badgeFinder.findById(8L);
            case 7 -> badgeFinder.findById(7L);
            case 3 -> badgeFinder.findById(6L);
            default -> null;
        };
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
