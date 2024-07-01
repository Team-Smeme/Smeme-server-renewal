package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.badge.Badge;

import java.util.List;

public interface BadgePort {
    List<Badge> findAll();
}
