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
import lombok.val;
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
    public Optional<Badge> findRecentlyByMember(long memberId) {
        return memberBadgeRepository.findFirstByMemberId(memberId)
                .map(memberBadge -> findBadge(memberBadge.getBadgeId()).toDomain());
    }

    @Override
    public Badge saveWelcomeBadgeToMember(long memberId) {
        val memberBadge = memberBadgeRepository.save(new MemberBadgeEntity(memberId, 1L));
        return findBadge(memberBadge.getBadgeId()).toDomain();
    }

    @Override
    public int countByMember(long memberId) {
        return memberBadgeRepository.countByMemberId(memberId);
    }

    @Override
    public Badge saveAcquiredBadge(long memberId, Badge badge) {
        val memberBadge = memberBadgeRepository.save(new MemberBadgeEntity(memberId, badge.getId()));
        return findBadge(memberBadge.getBadgeId()).toDomain();
    }

    @Override
    public List<Long> findIdsByMember(long memberId) {
        return memberBadgeRepository.findByMemberId(memberId).stream().map(MemberBadgeEntity::getBadgeId).toList();
    }

    private BadgeEntity findBadge(long id) {
        return badgeRepository.findById(id)
                .orElseThrow(() -> new SmeemException(
                        ExceptionCode.NOT_FOUND,
                        "(서버 개발자에게 문의: Badge ID: " + id + ")"));
    }
}
