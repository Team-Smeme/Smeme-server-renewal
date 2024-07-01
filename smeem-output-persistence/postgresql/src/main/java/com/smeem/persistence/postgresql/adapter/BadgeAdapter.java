package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.port.output.persistence.BadgePort;
import com.smeem.application.port.output.persistence.MemberBadgePort;
import com.smeem.persistence.postgresql.persistence.entity.BadgeEntity;
import com.smeem.persistence.postgresql.persistence.entity.MemberBadgeEntity;
import com.smeem.persistence.postgresql.persistence.repository.BadgeRepository;
import com.smeem.persistence.postgresql.persistence.repository.MemberBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BadgeAdapter implements BadgePort, MemberBadgePort {
    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;

    @Override
    public List<Badge> findAll() {
        return badgeRepository.findAllOrderById().stream().map(BadgeEntity::toDomain).toList();
    }

    @Override
    public List<Long> findIdsByMember(long memberId) {
        return memberBadgeRepository.findByMemberId(memberId).stream().map(MemberBadgeEntity::getId).toList();
    }
}
