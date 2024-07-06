package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.persistence.postgresql.persistence.entity.BadgeEntity;

import java.util.List;

public interface BadgeCustomRepository {
    List<BadgeEntity> findAllOrderById();
}
