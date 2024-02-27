package com.smeem.domain.badge.repository;


import com.smeem.domain.badge.model.Badge;

import java.util.List;

public interface BadgeCustomRepository {
    List<Badge> findAllOrderById();
}
