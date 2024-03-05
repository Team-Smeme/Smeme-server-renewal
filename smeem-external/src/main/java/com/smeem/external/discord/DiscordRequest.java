package com.smeem.external.discord;

import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiscordRequest(
        String content
) {

    public static DiscordRequest of(String content) {
        return DiscordRequest.builder()
                .content(content)
                .build();
    }
}
