package com.smeem.domain.badge.repository;


import com.smeem.domain.badge.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long>, BadgeCustomRepository {
}
