package com.smeem.application.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Getter
@ConfigurationProperties(prefix = "smeem")
public class SmeemProperties {
    private final Secret secret;
    private final Duration duration;
    private final Client client;

    public SmeemProperties(Secret secret, Duration duration, Client client) {
        this.secret = secret;
        this.duration = duration;
        this.client = client;
    }

    @Getter
    public static class Secret {
        private String key;

        public Secret(String key) {
            this.key = key;
        }

        @PostConstruct
        private void init() {
            this.key = Base64.getEncoder().encodeToString(key.getBytes(StandardCharsets.UTF_8));
        }
    }

    public record Duration(
            int remind,
            int expired
    ) {
    }

    public record Client(
            Version version
    ) {

        public record Version(
                String title,
                String content,
                APP ios,
                APP android
        ) {

            public record APP(
                    String app,
                    String force
            ) {
            }
        }
    }
}
