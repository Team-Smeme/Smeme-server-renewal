package com.smeem.notification;

import com.smeem.application.port.output.notification.NotificationPort;
import com.smeem.application.port.output.persistence.MemberPort;
import com.smeem.notification.firebase.FcmNotificationService;
import com.smeem.notification.firebase.dto.request.NotificationMulticastRequest;
import com.smeem.notification.firebase.dto.request.NotificationSingleRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationAdapter implements NotificationPort {
    private final FcmNotificationService notificationService;
    private final MemberPort memberPort;

    @Value("${smeem.notification.title}")
    private String NOTIFICATION_TITLE;
    @Value("${smeem.notification.body}")
    private String NOTIFICATION_BODY;

    @Override
    public void test(long memberId) {
        val member = memberPort.findById(memberId);
        notificationService.sendMessage(NotificationSingleRequest.of(
                member.getFcmToken(),
                "푸시 알림 테스트",
                "푸시 알림 테스트입니다."));
    }

    @Override
    public void sendNotificationForTrainingTime(List<String> fcmTokens) {
        notificationService.sendMessages(NotificationMulticastRequest.of(
                fcmTokens,
                NOTIFICATION_TITLE,
                NOTIFICATION_BODY));
    }
}
