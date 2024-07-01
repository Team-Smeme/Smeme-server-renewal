package com.smeem.application.domain.badge;

import com.smeem.application.port.input.BadgeUseCase;
import com.smeem.application.port.input.dto.response.badge.RetrieveBadgesResponse;
import com.smeem.application.port.input.dto.response.badge.RetrieveMemberBadgesResponse;
import com.smeem.application.port.output.persistence.BadgePort;
import com.smeem.application.port.output.persistence.DiaryPort;
import com.smeem.application.port.output.persistence.MemberBadgePort;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BadgeService implements BadgeUseCase {
    private final BadgePort badgePort;
    private final MemberBadgePort memberBadgePort;
    private final DiaryPort diaryPort;

    @Override
    public RetrieveBadgesResponse retrieveBadges(long memberId) {
        val acquiredBadgeIds = memberBadgePort.findIdsByMember(memberId);
        val typeToBadges = new LinkedHashMap<BadgeType, List<Badge>>();
        for (val badge : badgePort.findAll()) {
            typeToBadges.computeIfAbsent(badge.getBadgeType(), x -> new ArrayList<>()).add(badge);
        }
        return RetrieveBadgesResponse.from(typeToBadges, acquiredBadgeIds);
    }

    @Override
    public RetrieveMemberBadgesResponse retrieveMemberBadges(long memberId) {
        val badges = badgePort.findAll();
        val acquiredBadgeIds = memberBadgePort.findIdsByMember(memberId);
        val badgeTypeToPerformance = toMapMemberPerformanceByBadgeType(memberId);
        return RetrieveMemberBadgesResponse.from(badges, acquiredBadgeIds, badgeTypeToPerformance);
    }

    private Map<BadgeType, Integer> toMapMemberPerformanceByBadgeType(long memberId) {
        val badgeTypeToPerformance = new HashMap<BadgeType, Integer>();
        badgeTypeToPerformance.put(BadgeType.COUNTING, diaryPort.countByMember(memberId));
        return badgeTypeToPerformance;
    }
}
