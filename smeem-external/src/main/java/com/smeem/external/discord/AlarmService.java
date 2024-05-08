package com.smeem.external.discord;

public interface AlarmService {
    void send(String content, DiscordAlarmCase alarmCase);
}
