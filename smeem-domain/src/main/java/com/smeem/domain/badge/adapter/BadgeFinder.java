package com.smeem.domain.badge.adapter;

import com.smeem.domain.badge.exception.BadgeException;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.badge.repository.BadgeRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.smeem.common.code.failure.BadgeFailureCode.INVALID_BADGE;

@RepositoryAdapter
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

    public Optional<Badge> findBadgeByDiaryCount(final int diaryCount) {
        return switch (diaryCount) {
            case 50 -> badgeRepository.findById(5L);
            case 30 -> badgeRepository.findById(4L);
            case 10 -> badgeRepository.findById(3L);
            case 1 -> badgeRepository.findById(2L);
            default -> Optional.empty();
        };
    }

    public Optional<Badge> findBadgeByDiaryComboCount(final int diaryComboCount) {
        return switch (diaryComboCount) {
            case 30 -> badgeRepository.findById(9L);
            case 15 -> badgeRepository.findById(8L);
            case 7 -> badgeRepository.findById(7L);
            case 3 -> badgeRepository.findById(6L);
            default -> Optional.empty();
        };
    }
}
