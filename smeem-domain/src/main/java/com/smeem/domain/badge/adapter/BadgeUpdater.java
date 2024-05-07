package com.smeem.domain.badge.adapter;


import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class BadgeUpdater {

    public void updateAcquisitionRatio(Badge badge, float acquisitionRatio) {
        badge.updateBadgeAcquisitionRatio(acquisitionRatio);
    }
}
