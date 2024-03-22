package com.smeem.batch.scheduler;

import com.smeem.common.config.ValueConfig;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.external.firebase.FCMService;
import com.smeem.external.firebase.dto.request.NotificationMulticastRequest;
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
public class FCMScheduler {

    private final MemberRepository memberRepository;

    private final FCMService fcmService;
    private final ValueConfig valueConfig;

    @Scheduled(cron = "${fcm.cron_expression}")
    @Transactional(readOnly = true)
    public void sendMessagesForTrainingTime() throws InterruptedException {
        Thread.sleep(1000);

        val members = memberRepository.findAllByTrainingTimeForSendingMessage(LocalDateTime.now());

        if (!members.isEmpty()) {
            val tokens = members.stream().map(Member::getFcmToken).toList();
            val messageTitle = valueConfig.getMESSAGE_TITLE();
            val messageBody = valueConfig.getMESSAGE_BODY();
            fcmService.sendMessages(NotificationMulticastRequest.of(tokens, messageTitle, messageBody));
        }
    }
}
