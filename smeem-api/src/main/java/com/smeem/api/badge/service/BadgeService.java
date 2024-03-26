package com.smeem.api.badge.service;

import com.smeem.api.badge.service.dto.response.BadgeListServiceResponse;
import com.smeem.domain.badge.exception.BadgeException;
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
    public void saveMemberBadge(final Member member, final Badge badge) {
        memberBadgeRepository.save(MemberBadge.builder()
                .member(member)
                .badge(badge)
                .build());
    }
    public BadgeListServiceResponse getBadges(final long memberId) {
        val badges = badgeRepository.findAllOrderById();
        val badgeMap = classifiedByType(badges);
        val memberBadges = memberBadgeRepository.findAllByMemberId(memberId);
        return BadgeListServiceResponse.of(badgeMap, memberBadges);
    }

    public Badge get(final long id) {
        return badgeRepository.findById(id)
                .orElseThrow(() -> new BadgeException(INVALID_BADGE));
    }

    public Badge getBadgeByCountOfDiary(final int diaryCount) {
        return switch (diaryCount) {
            case 50 -> get(5L);
            case 30 -> get(4L);
            case 10 -> get(3L);
            case 1 -> get(2L);
            default -> null;
        };
    }

    public Badge getBadgeByComboCountOfDiary(final int diaryComboCount) {
        return switch (diaryComboCount) {
            case 30 -> get(9L);
            case 15 -> get(8L);
            case 7 -> get(7L);
            case 3 -> get(6L);
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
