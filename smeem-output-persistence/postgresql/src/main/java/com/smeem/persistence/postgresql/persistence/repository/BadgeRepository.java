package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.application.domain.badge.BadgeType;
import com.smeem.persistence.postgresql.persistence.entity.BadgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BadgeRepository extends JpaRepository<BadgeEntity, Long>, BadgeCustomRepository {
    Optional<BadgeEntity> findByBadgeTypeAndStandard(BadgeType badgeType, int standard);
}
