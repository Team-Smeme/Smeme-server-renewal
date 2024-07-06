package com.smeem.notification.firebase.dto.request;

import com.google.firebase.messaging.Notification;

public interface NotificationRequest {
    String title();
    String body();
    Notification toNotification();
}
