package com.smeem.external.discord;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiscordAlarmCase {
    ERROR("서버 에러 알라미"),
    INFO("서버 알리미");

    private final String message;
}
