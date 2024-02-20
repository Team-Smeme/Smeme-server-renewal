package com.smeem.external.firebase.dto.request;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MessagePushRequest(
        boolean validateOnly,
        MessageRequest message
) {

    public static MessagePushRequest of(MessagePushServiceRequest request) {
        return MessagePushRequest.builder()
                .validateOnly(false)
                .message(MessageRequest.of(request))
                .build();
    }

    @Builder(access = PRIVATE)
    record MessageRequest(
            NotificationRequest notification,
            String token
    ) {

        private static MessageRequest of(MessagePushServiceRequest request) {
            return MessageRequest.builder()
                    .notification(NotificationRequest.of(request))
                    .token(request.targetToken())
                    .build();
        }
    }

    @Builder(access = PRIVATE)
    record NotificationRequest(
            String title,
            String body
    ) {

        private static NotificationRequest of(MessagePushServiceRequest request) {
            return NotificationRequest.builder()
                    .title(request.title())
                    .body(request.body())
                    .build();
        }
    }
}
