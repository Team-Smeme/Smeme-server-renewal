package com.smeem.external.firebase;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MessageRequest(
        boolean validateOnly,
        Message message
) {
    public static MessageRequest of(String targetToken, String title, String body) {
        return MessageRequest.builder()
                .validateOnly(false)
                .message(Message.of(title, body, targetToken))
                .build();
    }

    @Builder(access = PRIVATE)
    record Message(
            NotificationRequest notification,
            String token
    ) {

        private static Message of(String title, String body, String token) {
            return Message.builder()
                    .notification(NotificationRequest.of(title, body))
                    .token(token)
                    .build();
        }
    }

    @Builder(access = PRIVATE)
    record NotificationRequest(
            String title,
            String body
    ) {

        private static NotificationRequest of(String title, String body) {
            return NotificationRequest.builder()
                    .title(title)
                    .body(body)
                    .build();
        }
    }
}
