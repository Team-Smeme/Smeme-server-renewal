package com.smeem.domain.badge.adapter;


import com.smeem.domain.persistence.entity.BadgeEntity;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class BadgeUpdater {

    public void updateAcquisitionRatio(BadgeEntity badge, float acquisitionRatio) {
        badge.updateBadgeAcquisitionRatio(acquisitionRatio);
    }
}
