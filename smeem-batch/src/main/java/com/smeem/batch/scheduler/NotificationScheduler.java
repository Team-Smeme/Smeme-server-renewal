package com.smeem.batch.scheduler;

import com.smeem.common.config.ValueConfig;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.external.firebase.NotificationService;
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
public class NotificationScheduler {

    private final MemberRepository memberRepository;

    private final NotificationService notificationService;
    private final ValueConfig valueConfig;

    @Scheduled(cron = "${smeem.notification.cron_expression}")
    @Transactional(readOnly = true)
    public void sendMessagesForTrainingTime() throws InterruptedException {
        Thread.sleep(1000);

        val members = memberRepository.findAllByTrainingTime(LocalDateTime.now());

        if (!members.isEmpty()) {
            val tokens = members.stream().map(Member::getFcmToken).toList();
            val title = valueConfig.getNOTIFICATION_TITLE();
            val body = valueConfig.getNOTIFICATION_BODY();
            notificationService.sendMessages(NotificationMulticastRequest.of(tokens, title, body));
        }
    }
}
