package com.smeem.notice.discord.dto;

import lombok.Builder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiscordRequest(
        String content,
        List<Embed> embeds
) {

    public static DiscordRequest of(DiscordMessage message) {
        return DiscordRequest.builder()
                .content(message.title())
                .embeds(List.of(Embed.of(message)))
                .build();
    }

    @Builder(access = PRIVATE)
    private record Embed(
            String title,
            String description
    ) {

        private static Embed of(DiscordMessage message) {
            return Embed.builder()
                    .title(message.noticeType().getName())
                    .description("### ⏰ 발생 시간 \n"
                            + message.sendAt()
                            + "\n### 📢 안내 내용\n"
                            + message.content())
                    .build();
        }
    }
}
