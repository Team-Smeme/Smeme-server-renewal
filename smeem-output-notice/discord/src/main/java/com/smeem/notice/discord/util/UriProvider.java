package com.smeem.notice.discord.util;

import com.smeem.application.port.output.notice.NoticeType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

@Component
public class UriProvider {
    private final View error;
    @Value("${discord.webhook.url.sign-in}")
    String signInUrl;
    @Value("${discord.webhook.url.withdraw}")
    String withdrawUrl;
    @Value("${discord.webhook.url.error}")
    String errorUrl;

    public UriProvider(View error) {
        this.error = error;
    }

    public String getUri(NoticeType noticeType) {
        return switch (noticeType) {
            case SIGN_IN -> signInUrl;
            case WITHDRAW -> withdrawUrl;
            case ERROR -> errorUrl;
        };
    }
}
