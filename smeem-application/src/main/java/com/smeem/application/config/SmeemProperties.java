package com.smeem.application.config;

import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "smeem")
public class SmeemProperties {
    private final Secret secret;
    private final Duration duration;
    private final Client client;
    private final Limit limit;

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

    public record Limit(
            int correction
    ) {

        public void validateCorrectionLimit(int correctionCount) {
            if (correctionCount >= correction) {
                throw new SmeemException(ExceptionCode.EXCEED_CORRECTION_LIMIT);
            }
        }

    }
}
