package com.smeem.batch.scheduler;

import com.smeem.application.port.input.DiaryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryScheduler {
    private final DiaryUseCase diaryUseCase;

    @Value("${smeem.duration.expired}")
    private int DURATION_EXPIRED;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredDiaries() {
        diaryUseCase.deleteExpiredDiaries(DURATION_EXPIRED);
    }
}
