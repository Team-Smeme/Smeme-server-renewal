package com.smeem.scheduler;

import com.smeme.server.config.ValueConfig;
import com.smeme.server.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class MessageScheduler {
    private final MessageService messageService;
    private final ValueConfig valueConfig;

    @Scheduled(cron = "${fcm.cron_expression}")
    public void pushMessage() throws InterruptedException {
        Thread.sleep(1000);
        messageService.pushMessageForTrainingTime(LocalDateTime.now(), valueConfig.getMESSAGE_TITLE(), valueConfig.getMESSAGE_BODY());
    }
}
