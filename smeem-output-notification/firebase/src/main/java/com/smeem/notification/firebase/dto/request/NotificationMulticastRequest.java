package com.smeem.notification.firebase.dto.request;

import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record NotificationMulticastRequest(
        @NonNull List<String> targetTokens,
        String title,
        String body
) implements NotificationRequest {

    public static NotificationMulticastRequest of(List<String> tokens, String title, String body) {
        return NotificationMulticastRequest.builder()
                .targetTokens(tokens)
                .title(title)
                .body(body)
                .build();
    }

    public MulticastMessage.Builder buildSendMessage() {
        return MulticastMessage.builder()
                .setNotification(toNotification())
                .addAllTokens(targetTokens);
    }

    public Notification toNotification() {
        return Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
    }
}
