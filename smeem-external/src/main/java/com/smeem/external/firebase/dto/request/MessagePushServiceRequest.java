package com.smeem.external.firebase.dto.request;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record MessagePushServiceRequest(
        String targetToken,
        String title,
        String body
) {

    public static MessagePushServiceRequest of(String token, String title, String body) {
        return MessagePushServiceRequest.builder()
                .targetToken(token)
                .title(title)
                .body(body)
                .build();
    }
}
