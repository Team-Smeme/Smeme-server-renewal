package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.badge.BadgeType;

import java.util.List;
import java.util.Optional;

public interface BadgePort {
    List<Badge> findAll();
    Optional<Badge> findByBadgeTypeAndStandard(BadgeType badgeType, int standard);
    Optional<Badge> findRecentlyByMember(long memberId);
    Badge saveWelcomeBadgeToMember(long memberId);
    int countByMember(long memberId);
}
