package com.smeem.notice.discord.util;

import com.smeem.application.port.output.notice.NoticeType;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UriProvider {
    @Value("${spring.config.activate.on-profile}")
    String profile;
    @Value("${discord.webhook.url.prod.sign-in}")
    String signInUrl;
    @Value("${discord.webhook.url.prod.withdraw}")
    String withdrawUrl;
    @Value("${discord.webhook.url.prod.error}")
    String prodErrorUrl;
    @Value("${discord.webhook.url.dev.error}")
    String devErrorUrl;
    @Value("${discord.webhook.url.github}")
    String githubUrl;

    public String getUri(NoticeType noticeType) {
        return switch (noticeType) {
            case SIGN_IN -> signInUrl;
            case WITHDRAW -> withdrawUrl;
            case GITHUB -> githubUrl;
            case ERROR -> getErrorUrl(profile);
        };
    }

    private String getErrorUrl(String profile) {
        return switch (profile) {
            case "dev" -> devErrorUrl;
            case "prod" -> prodErrorUrl;
            default -> throw new SmeemException(ExceptionCode.NOT_FOUND, "유효하지 않은 profile");
        };
    }
}
