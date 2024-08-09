package com.smeem.common.logger.discord;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class DiscordHookLink {
    public final String signInUrl;
    public final String withdrawUrl;
    public final String errorUrl;

    DiscordHookLink(
            @Value("${discord.webhook.url.sign-in}") String signInUrl,
            @Value("${discord.webhook.url.withdraw}") String withdrawUrl,
            @Value("${discord.webhook.url.error}") String errorUrl
    ) {
        this.signInUrl = signInUrl;
        this.withdrawUrl = withdrawUrl;
        this.errorUrl = errorUrl;
    }
}
