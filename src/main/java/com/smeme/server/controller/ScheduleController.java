package com.smeme.server.controller;

import java.time.LocalDateTime;

import com.smeme.server.config.ValueConfig;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.service.DiaryService;
import com.smeme.server.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleController {

    private final MessageService messageService;
    private final DiaryService diaryService;
    private final ValueConfig valueConfig;

    @Scheduled(cron = "0 0/30 * * * *")
    public void pushMessage() throws InterruptedException {
        Thread.sleep(1000);
        messageService.pushMessageForTrainingTime(LocalDateTime.now(), valueConfig.getMESSAGE_TITLE(), valueConfig.getMESSAGE_BODY());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteDiaries30Past() throws InterruptedException {
        Thread.sleep(1000);
        diaryService.deleteDiary30Past(LocalDateTime.now().minusDays(30));
    }
}
