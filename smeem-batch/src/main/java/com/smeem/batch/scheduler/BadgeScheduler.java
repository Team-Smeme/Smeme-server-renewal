package com.smeem.batch.scheduler;


import com.smeem.batch.scheduler.support.Scheduler;
import com.smeem.domain.badge.adapter.BadgeFinder;
import com.smeem.domain.badge.adapter.BadgeUpdater;
import com.smeem.domain.persistence.entity.BadgeEntity;
import com.smeem.domain.member.adapter.member.MemberCounter;
import com.smeem.domain.member.adapter.memberbadge.MemberBadgeCounter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Scheduler
@RequiredArgsConstructor
public class BadgeScheduler {

    private final BadgeUpdater badgeUpdater;
    private final BadgeFinder badgeFinder;
    private final MemberCounter memberCounter;
    private final MemberBadgeCounter memberBadgeCounter;

    @Scheduled(cron = "${smeem.badge.update_cron_expression}")
    @Transactional
    public void updateBadgeAcquisitionRatio() {
        badgeFinder.findAll()
                .forEach(this::updateEachBadge);
    }

    private void updateEachBadge(BadgeEntity badge) {
        val badgeAcquisitionRatio = calculateBadgeAcquisitionRatio(badge);
        badgeUpdater.updateAcquisitionRatio(badge, badgeAcquisitionRatio);
    }

    private float calculateBadgeAcquisitionRatio(final BadgeEntity badge) {
        val memberCount = memberCounter.count();
        val badgeCount = memberBadgeCounter.countByBadge(badge);
        return postProcess((float) badgeCount / memberCount);
    }

    private float postProcess(final float point) {
        if (point < 10.0f) {
            return (float) Math.ceil(point * 2f) / 2f;
        } else {
            return (float) (Math.ceil(point / 10f) * 10);
        }
    }
}
