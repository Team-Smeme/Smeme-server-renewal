package com.smeme.server.service;

import com.smeme.server.dto.badge.BadgeListResponseDTO;
import com.smeme.server.model.Member;
import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.BadgeType;
import com.smeme.server.model.badge.MemberBadge;
import com.smeme.server.repository.badge.BadgeRepository;
import com.smeme.server.repository.badge.MemberBadgeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.smeme.server.util.message.ErrorMessage.INVALID_BADGE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeService {

    private final MemberBadgeRepository memberBadgeRepository;
    private final BadgeRepository badgeRepository;

    public BadgeListResponseDTO getBadgeList(Long memberId) {
        val badges = badgeRepository.findAllOrderById();
        val badgeMap = classifiedByType(badges);
        val memberBadges = memberBadgeRepository.findAllByMemberId(memberId);
        return BadgeListResponseDTO.of(badgeMap, memberBadges);
    }

    private Map<BadgeType, List<Badge>> classifiedByType(List<Badge> badges) {
        val badgeMap = new HashMap<BadgeType, List<Badge>>();
        badges.forEach(badge -> putBadgeMap(badgeMap, badge));
        return badgeMap;
    }

    private void putBadgeMap(Map<BadgeType, List<Badge>> badgeMap, Badge badge) {
        badgeMap.computeIfAbsent(badge.getType(), k -> new ArrayList<>()).add(badge);
    }

    @Transactional
    public void saveMemberBadge(Member member, Badge badge) {
        memberBadgeRepository.save(new MemberBadge(member, badge));
    }

    protected Badge get(Long id) {
        return badgeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_BADGE.getMessage()));
    }
}
