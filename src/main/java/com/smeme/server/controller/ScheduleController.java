package com.smeme.server.controller;

import java.time.LocalDateTime;

import com.smeme.server.config.ValueConfig;
import com.smeme.server.repository.diary.DiaryRepository;
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
    private final DiaryRepository diaryRepository;

//    @Scheduled(cron = "0 0/30 * * * *")
    public void pushMessage() throws InterruptedException {
        Thread.sleep(1000);
        messageService.pushMessageForTrainingTime(LocalDateTime.now(), valueConfig.getMESSAGE_TITLE(), valueConfig.getMESSAGE_BODY());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredDiaries() {
        diaryService.deleteExpiredDiary();
    }

    //TODO: DB 작업 과정 중 삭제된 Diary 데이터 이동
    public void copyToDeletedDiary() {
        diaryRepository.findDeleted().forEach(diary -> diaryService.delete(diary.getId()));
    }
}
