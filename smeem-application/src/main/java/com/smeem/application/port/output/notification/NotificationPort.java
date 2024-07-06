package com.smeem.application.port.output.notification;

import java.util.List;

public interface NotificationPort {
    void test(long memberId);
    void sendNotificationForTrainingTime(List<String> fcmTokens);
}
