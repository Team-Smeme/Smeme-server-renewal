package com.smeme.server.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
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

	@Scheduled(cron = "0 0/30 * * * *")
	public void pushMessage() throws InterruptedException {
		Thread.sleep(1000);
		messageService.pushMessageForTrainingTime(LocalDateTime.now());
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void deleteDiaries30Past() throws InterruptedException {
		Thread.sleep(1000);
		diaryService.deleteDiary30Past(LocalDateTime.now().minusDays(30));
	}
}
