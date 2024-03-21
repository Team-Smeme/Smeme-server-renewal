package com.smeem.domain.badge.adapter;


import com.smeem.common.exception.BadgeException;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.smeem.common.code.failure.BadgeFailureCode.INVALID_BADGE;

@Component
@RequiredArgsConstructor
public class BadgeFinder {

    private final BadgeRepository badgeRepository;


    public Badge findById(final long id) {
        return badgeRepository.findById(id)
                .orElseThrow(() -> new BadgeException(INVALID_BADGE));
    }

    public List<Badge> findAllOrderById() {
        return badgeRepository.findAllOrderById();
    }

}
