package com.smeem.external.firebase;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MessageRequest(
        boolean validateOnly,
        MessageRequest message
) {
    public static com.smeem.external.firebase.MessageRequest of(String targetToken, String title, String body) {
        return com.smeem.external.firebase.MessageRequest.builder()
                .validateOnly(false)
                .message(com.smeem.external.firebase.MessageRequest.MessageRequest.of(title, body, targetToken))
                .build();
    }

    @Builder(access = PRIVATE)
    record MessageRequest(
            NotificationRequest notification,
            String token
    ) {

        private static MessageRequest of(String title, String body, String token) {
            return com.smeem.external.firebase.MessageRequest.MessageRequest.builder()
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
