package com.smeme.server.repository.badge;

import com.smeme.server.model.badge.Badge;

import java.util.List;

public interface BadgeCustomRepository {
    List<Badge> findAllOrderById();
}
