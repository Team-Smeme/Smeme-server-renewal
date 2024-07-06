package com.smeem.notice.discord.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiscordRequest(
        String content,
        List<Embed> embeds
) {

    public static DiscordRequest of(DiscordMessage message) {
        return DiscordRequest.builder()
                .content("# " + message.title())
                .embeds(List.of(Embed.of(message)))
                .build();
    }

    public static DiscordRequest of(DiscordErrorMessage message) {
        return DiscordRequest.builder()
                .content("# " + message.title())
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
                    .title(message.noticeType().name())
                    .description("### ⏰ 발생 시간 \n"
                            + message.sendAt()
                            + "### 📢 안내 내용"
                            + message.content())
                    .build();
        }

        private static Embed of(DiscordErrorMessage message) {
            return Embed.builder()
                    .title(message.noticeType().name())
                    .description("### ⏰ 발생 시간 \n"
                            + message.requestedAt()
                            + "### 🔗 요청 URI"
                            + message.requestUri()
                            + "### 📢 에러 내용"
                            + message.content())
                    .build();
        }
    }
}
