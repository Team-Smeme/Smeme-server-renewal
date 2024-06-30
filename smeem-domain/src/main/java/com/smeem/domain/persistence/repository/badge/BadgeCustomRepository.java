package com.smeem.domain.persistence.repository.badge;


import com.smeem.domain.persistence.entity.BadgeEntity;

import java.util.List;

public interface BadgeCustomRepository {
    List<BadgeEntity> findAllOrderById();
}
