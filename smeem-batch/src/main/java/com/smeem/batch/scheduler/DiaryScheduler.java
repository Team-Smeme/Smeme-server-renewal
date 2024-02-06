package com.smeem.batch.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class DiaryScheduler {
    private final DiaryService diaryService;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredDiaries() {
        diaryService.deleteExpiredDiary();
    }
}
