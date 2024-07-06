package com.smeem.notice.discord;

import com.smeem.notice.discord.dto.DiscordErrorMessage;
import com.smeem.notice.discord.dto.DiscordMessage;
import com.smeem.notice.discord.dto.DiscordRequest;
import com.smeem.notice.discord.util.Constant;
import com.smeem.notice.discord.util.UriProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiscordNoticeService {
    private final UriProvider uriProvider;

    public void notice(DiscordMessage message) {
        try {
            RestClient.create()
                    .post()
                    .uri(uriProvider.getUri(message.noticeType()))
                    .header(HttpHeaders.ACCEPT, Constant.APPLICATION_JSON_UTF8_VALUE)
                    .header(HttpHeaders.CONTENT_TYPE, Constant.APPLICATION_JSON_UTF8_VALUE)
                    .body(DiscordRequest.of(message))
                    .retrieve();
        } catch (RuntimeException exception) {
            log.error("Discord Error", exception);
        }
    }

    public void notice(DiscordErrorMessage message) {
        try {
            RestClient.create()
                    .post()
                    .uri(uriProvider.getUri(message.noticeType()))
                    .header(HttpHeaders.ACCEPT, Constant.APPLICATION_JSON_UTF8_VALUE)
                    .header(HttpHeaders.CONTENT_TYPE, Constant.APPLICATION_JSON_UTF8_VALUE)
                    .body(DiscordRequest.of(message))
                    .retrieve();
        } catch (RuntimeException exception) {
            log.error("Discord Error", exception);
        }
    }
}
