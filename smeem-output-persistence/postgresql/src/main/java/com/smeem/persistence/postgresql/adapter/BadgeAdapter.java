package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.badge.BadgeType;
import com.smeem.application.port.output.persistence.BadgePort;
import com.smeem.application.port.output.persistence.MemberBadgePort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.persistence.postgresql.persistence.entity.BadgeEntity;
import com.smeem.persistence.postgresql.persistence.entity.MemberBadgeEntity;
import com.smeem.persistence.postgresql.persistence.repository.BadgeRepository;
import com.smeem.persistence.postgresql.persistence.repository.MemberBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Optional<Badge> findByBadgeTypeAndStandard(BadgeType badgeType, int standard) {
        return badgeRepository.findByBadgeTypeAndStandard(badgeType, standard)
                .map(BadgeEntity::toDomain);
    }

    @Override
    public List<Long> findIdsByMember(long memberId) {
        return memberBadgeRepository.findByMemberId(memberId).stream().map(MemberBadgeEntity::getId).toList();
    }

    @Override
    public Badge save(long memberId, long badgeId) {
        memberBadgeRepository.save(new MemberBadgeEntity(memberId, badgeId));
        return find(badgeId).toDomain();
    }

    private BadgeEntity find(long id) {
        return badgeRepository.findById(id)
                .orElseThrow(() -> new SmeemException(ExceptionCode.NOT_FOUND, "id: " + id));
    }
}
