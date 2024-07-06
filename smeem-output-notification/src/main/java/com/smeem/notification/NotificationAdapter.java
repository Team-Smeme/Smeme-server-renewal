package com.smeem.notification;

import com.smeem.application.port.output.notification.NotificationPort;
import com.smeem.application.port.output.persistence.MemberPort;
import com.smeem.notification.firebase.FcmNotificationService;
import com.smeem.notification.firebase.dto.request.NotificationSingleRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationAdapter implements NotificationPort {
    private final FcmNotificationService notificationService;
    private final MemberPort memberPort;

    @Override
    public void test(long memberId) {
        val member = memberPort.findById(memberId);
        notificationService.sendMessage(NotificationSingleRequest.of(
                member.getFcmToken(),
                "푸시 알림 테스트",
                "푸시 알림 테스트입니다."));
    }
}
