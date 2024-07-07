package com.smeem.common.logger.discord;

import com.smeem.common.logger.HookLogger;
import com.smeem.common.logger.LoggerType;
import com.smeem.common.logger.LoggingMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class DiscordHookLogger implements HookLogger {
    private final String signInUrl;
    private final String withdrawUrl;
    private final String errorUrl;
    private final static String APPLICATION_JSON_UTF8_VALUE = "application/json; UTF-8";

    DiscordHookLogger(
            @Value("${discord.webhook.url.sign-in}") String signInUrl,
            @Value("${discord.webhook.url.withdraw}") String withdrawUrl,
            @Value("${discord.webhook.url.error}") String errorUrl
    ) {
        this.signInUrl = signInUrl;
        this.withdrawUrl = withdrawUrl;
        this.errorUrl = errorUrl;
    }

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
            case SIGN_IN -> signInUrl;
            case WITHDRAW -> withdrawUrl;
            case ERROR -> errorUrl;
        };
    }
}
