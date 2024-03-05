package com.smeem.external.discord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smeem.common.config.ValueConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.ACCEPT;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiscordAlarmSender {

    private final ObjectMapper objectMapper;
    private final ValueConfig valueConfig;
    private final Environment environment;

    public void send(final String content, final DiscordAlarmCase alarmCase) {
        try {
            val restClient = RestClient.create();
            restClient.post()
                    .uri(webHookUri(alarmCase))
                    .header(ACCEPT, "application/json; UTF-8")
                    .body(makeRequestBody(content))
                    .retrieve();
        } catch (RuntimeException | JsonProcessingException e) {
            log.error("디스코드 알람 전송에 실패했습니다.", e);
            // TODO: add Sentry Capture Exception
        }

    }

    private String makeRequestBody(String content) throws JsonProcessingException {
        val request = DiscordRequest.of("[" + getCurrentProfile() + "] : " + content);
        return objectMapper.writeValueAsString(request);
    }

    private String getCurrentProfile() {
        if (environment.getActiveProfiles().length == 0) {
            return "default";
        }
        return environment.getActiveProfiles()[0];
    }

    private String webHookUri(DiscordAlarmCase alarmCase) {
        return switch (alarmCase) {
            case ERROR -> valueConfig.getDISCORD_WEBHOOK_ERROR_URL();
            case INFO -> valueConfig.getDISCORD_WEBHOOK_INFO_URL();
        };
    }
}
