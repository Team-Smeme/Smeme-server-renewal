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

	@Value("${fcm.smeem_title}")
	private String MESSAGE_TITLE;
	@Value("${fcm.smeem_body}")
	private String MESSAGE_BODY;

	@Scheduled(cron = "0 0/30 * * * *")
	public void pushMessage() throws InterruptedException {
		Thread.sleep(1000);
		messageService.pushMessageForTrainingTime(LocalDateTime.now(), MESSAGE_TITLE, MESSAGE_BODY);
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void deleteDiaries30Past() throws InterruptedException {
		Thread.sleep(1000);
		diaryService.deleteDiary30Past(LocalDateTime.now().minusDays(30));
	}
}
