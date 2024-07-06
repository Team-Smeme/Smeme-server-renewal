package com.smeem.batch.scheduler;

import com.smeem.application.domain.member.Member;
import com.smeem.application.port.output.notification.NotificationPort;
import com.smeem.application.port.output.persistence.MemberPort;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {
    private final MemberPort memberPort;
    private final NotificationPort notificationPort;

    @Scheduled(cron = "${smeem.notification.cron_expression}")
    public void pushAlarmByTrainingTime() throws InterruptedException {
        Thread.sleep(1000);
        val members = memberPort.findByTrainingTime(LocalDateTime.now());
        if (!members.isEmpty()) {
            notificationPort.sendNotificationForTrainingTime(members.stream().map(Member::getFcmToken).toList());
        }
    }
}
