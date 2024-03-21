package com.smeem.api.common.schedulerTemp;

import com.smeem.common.config.ValueConfig;
import com.smeem.domain.training.model.TrainingTime;
import com.smeem.domain.training.repository.TrainingTimeRepository;
import com.smeem.external.firebase.FcmService;
import com.smeem.external.firebase.dto.request.MessagePushServiceRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class MessageScheduler {

    private final FcmService fcmService;
    private final TrainingTimeRepository trainingTimeRepository;
    private final ValueConfig valueConfig;

    @Scheduled(cron = "${fcm.cron_expression}")
    @Transactional(readOnly = true)
    public void pushMessagesForTrainingTime() throws InterruptedException {
        Thread.sleep(1000);
        val trainingTimes = trainingTimeRepository.getTrainingTimeForPushAlarm(LocalDateTime.now());
        trainingTimes.forEach(this::pushMessageForTrainingTime);
    }

    private void pushMessageForTrainingTime(TrainingTime trainingTime) {
        val fcmToken = trainingTime.getMember().getFcmToken();
        val messageTitle = valueConfig.getMESSAGE_TITLE();
        val messageBody = valueConfig.getMESSAGE_BODY();
        fcmService.pushMessage(MessagePushServiceRequest.of(fcmToken, messageTitle, messageBody));
    }
}
