package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.badge.Badge;

import java.util.List;

public interface MemberBadgePort {
    List<Long> findIdsByMember(long memberId);
    Badge save(long memberId, long badgeId); //TODO: 배지 가지고 있으면 그냥 배지만 반환
}
