package com.smeem.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discord")
public record DiscordProperties(
        Webhook webhook
) {

    public record Webhook(
            Url url
    ) {

        public record Url(
           String sign_in,
           String withdraw,
           String error
        ) {
        }
    }
}
