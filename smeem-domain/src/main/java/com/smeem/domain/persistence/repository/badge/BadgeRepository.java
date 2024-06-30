package com.smeem.domain.persistence.repository.badge;


import com.smeem.domain.persistence.entity.BadgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<BadgeEntity, Long>, BadgeCustomRepository {
}
