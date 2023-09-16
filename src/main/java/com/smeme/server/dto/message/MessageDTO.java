package com.smeme.server.dto.message;

import lombok.Builder;

@Builder
public record MessageDTO(
        boolean validateOnly,
        MessageVO message
) {
    public static MessageDTO of(String targetToken, String title, String body) {
        NotificationVO notification = new NotificationVO(title, body);
        MessageVO message = new MessageVO(notification, targetToken);
        return new MessageDTO(false, message);
    }

    record MessageVO(NotificationVO notification, String token) {
    }

    record NotificationVO(String title, String body) {
    }
}
