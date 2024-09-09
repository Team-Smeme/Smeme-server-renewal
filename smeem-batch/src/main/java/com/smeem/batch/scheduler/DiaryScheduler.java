package com.smeem.batch.scheduler;

import com.smeem.application.config.SmeemProperties;
import com.smeem.application.port.input.DiaryUseCase;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryScheduler {
    private final DiaryUseCase diaryUseCase;
    private final SmeemProperties smeemProperties;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredDiaries() {
        val expiredDuration = smeemProperties.getDuration().expired();
        diaryUseCase.deleteExpiredDiaries(expiredDuration);
    }
}
