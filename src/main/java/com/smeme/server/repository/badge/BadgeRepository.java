package com.smeme.server.repository.badge;

import com.smeme.server.model.badge.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

}
