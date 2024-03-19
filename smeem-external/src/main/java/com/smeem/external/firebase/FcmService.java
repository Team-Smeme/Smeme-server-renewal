package com.smeem.external.firebase;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.smeem.common.config.ValueConfig;
import com.smeem.common.exception.FcmException;
import com.smeem.external.firebase.dto.request.MessagePushRequest;
import com.smeem.external.firebase.dto.request.MessagePushServiceRequest;
import lombok.val;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

import static com.smeem.common.code.failure.FcmFailureCode.*;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class FcmService {

    private final ObjectMapper objectMapper;
    private final ValueConfig valueConfig;

    public void pushMessage(final MessagePushServiceRequest request) {
        val restClient = RestClient.create();
        restClient.post()
                .uri(valueConfig.getFIREBASE_API_URI())
                .contentType(APPLICATION_JSON)
                .body(makeMessage(request))
                .header(AUTHORIZATION, "Bearer " + getAccessToken())
                .header(ACCEPT, "application/json; UTF-8")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (fcmRequest, fcmResponse) -> {
                    throw new FcmException(INVALID_REQUEST_MESSAGE, fcmResponse.getStatusCode());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (fcmRequest, fcmResponse) -> {
                    throw new FcmException(INVALID_REQUEST_URI, fcmResponse.getStatusCode());
                })
                .toBodilessEntity();
    }

    private String makeMessage(MessagePushServiceRequest request) {
        try {
            val message = MessagePushRequest.of(request);
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException exception) {
            throw new FcmException(INVALID_REQUEST_PATTERN);
        }
    }

    private String getAccessToken() {
        try {
            val googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(valueConfig.getFIREBASE_CONFIG_PATH()).getInputStream())
                    .createScoped(List.of(valueConfig.getGOOGLE_API_URI()));
            googleCredentials.refreshIfExpired();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (IOException exception) {
            throw new FcmException(INVALID_REQUEST_PATTERN);
        }
    }
}
