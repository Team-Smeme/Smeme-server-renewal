package com.smeem.application.domain.visit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@EnableScheduling
@Slf4j
@Component
public class MemberVisitStore {

    private static final Map<LocalDate, Set<Long>> visitHistory = new ConcurrentHashMap<>();

    private static final int ONE_WEEK_DAYS = 7;

    /**
     * 매일 자정마다 clean up
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void cleanupOldVisits() {
        LocalDate oneWeekAgo = LocalDate.now().minusDays(ONE_WEEK_DAYS);
        visitHistory.keySet().removeIf(date -> date.isBefore(oneWeekAgo));
        log.info("[MemberVisitStore] clean up visitHistory before {}", oneWeekAgo);
    }

    public boolean isVisit(long memberId) {
        return Optional.ofNullable(visitHistory.get(LocalDate.now()))
                .map(it -> it.contains(memberId))
                .orElse(false);
    }

    public void visit(long memberId) {
        Set<Long> todayVisitors = visitHistory.computeIfAbsent(LocalDate.now(), k -> new HashSet<>());
        if (todayVisitors.contains(memberId)) {
            return;
        }
        todayVisitors.add(memberId);
    }
}
