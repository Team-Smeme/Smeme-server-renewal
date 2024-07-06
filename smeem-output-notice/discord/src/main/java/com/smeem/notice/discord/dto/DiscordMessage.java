package com.smeem.notice.discord.dto;

import com.smeem.application.port.output.notice.NoticeType;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDate;

@Builder(access = AccessLevel.PRIVATE)
public record DiscordMessage(
        String title,
        String content,
        LocalDate sendAt,
        NoticeType noticeType
) {

    public static DiscordMessage of(String title, String content, NoticeType noticeType) {
        return DiscordMessage.builder()
                .title(title)
                .content(content)
                .sendAt(LocalDate.now())
                .noticeType(noticeType)
                .build();
    }
}
