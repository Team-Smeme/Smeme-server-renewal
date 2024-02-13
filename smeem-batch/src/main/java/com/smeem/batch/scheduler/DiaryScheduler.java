package com.smeem.batch.scheduler;

import com.smeem.common.config.ValueConfig;
import com.smeem.domain.diary.repository.DeletedDiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class DiaryScheduler {

    private final DeletedDiaryRepository deletedDiaryRepository;
    private final ValueConfig valueConfig;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteExpiredDiaries() {
        val expiryDate = getExpiryDate();
        deletedDiaryRepository.deleteByUpdatedAtBefore(expiryDate);
    }

    private LocalDateTime getExpiryDate() {
        val expiredDay = valueConfig.getDURATION_EXPIRED() - 1;
        return LocalDate.now().minusDays(expiredDay).atStartOfDay();
    }
}
