package com.smeem.notification.firebase.dto.request;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record NotificationSingleRequest(
        @NonNull String targetToken,
        String title,
        String body
) implements NotificationRequest {

    public static NotificationSingleRequest of(String token, String title, String body) {
        return NotificationSingleRequest.builder()
                .targetToken(token)
                .title(title)
                .body(body)
                .build();
    }

    public Message.Builder buildMessage() {
        return Message.builder()
                .setToken(targetToken)
                .setNotification(toNotification());
    }

    public Notification toNotification() {
        return Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
    }
}
