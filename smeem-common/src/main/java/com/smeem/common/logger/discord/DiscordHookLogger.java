package com.smeem.common.logger.discord;

import com.smeem.common.config.DiscordProperties;
import com.smeem.common.logger.HookLogger;
import com.smeem.common.logger.LoggerType;
import com.smeem.common.logger.LoggingMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class DiscordHookLogger implements HookLogger {
    private final DiscordProperties discordProperties;

    private final static String APPLICATION_JSON_UTF8_VALUE = "application/json; UTF-8";

    @Override
    public void send(LoggingMessage loggingMessage) {
        try {
            RestClient.create()
                    .post()
                    .uri(getWebhookUrl(loggingMessage.noticeType()))
                    .header(HttpHeaders.ACCEPT, APPLICATION_JSON_UTF8_VALUE)
                    .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                    .body(DiscordRequest.of(loggingMessage))
                    .retrieve();
        } catch (RuntimeException exception) {
            log.error("Discord Error", exception);
        }
    }

    private String getWebhookUrl(LoggerType loggerType) {
        return switch (loggerType) {
            case SIGN_IN -> discordProperties.webhook().url().sign_in();
            case WITHDRAW -> discordProperties.webhook().url().withdraw();
            case ERROR -> discordProperties.webhook().url().error();
            case SURVEY -> discordProperties.webhook().url().survey();
        };
    }
}
