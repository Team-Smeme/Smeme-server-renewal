package com.smeem.external.firebase;


import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.smeem.common.config.ValueConfig;
import com.smeem.common.exception.FcmException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.smeem.common.code.failure.FcmFailureCode.INVALID_REQUEST_MESSAGE;
import static com.smeem.common.code.failure.FcmFailureCode.INVALID_REQUEST_PATTERN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class FcmService {

    private final ObjectMapper objectMapper;
    private final ValueConfig valueConfig;

    public void pushMessage(String targetToken, String title, String body) {
        try {
            String message = makeMessage(targetToken, title, body);
            RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

            Request request = new Request.Builder()
                    .url(valueConfig.getFIREBASE_API_URI())
                    .post(requestBody)
                    .addHeader(AUTHORIZATION, "Bearer " + getAccessToken())
                    .addHeader("Accept", "application/json; UTF-8")
                    .build();

            OkHttpClient client = new OkHttpClient();
            client.newCall(request).execute();
        } catch (Exception exception) {
            throw new FcmException(INVALID_REQUEST_MESSAGE);
        }
    }

    private String makeMessage(String targetToken, String title, String body) {
        try {
            MessageDTO message = MessageDTO.of(targetToken, title, body);
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException exception) {
            throw new FcmException(INVALID_REQUEST_PATTERN);
        }
    }

    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(valueConfig.getFIREBASE_CONFIG_PATH()).getInputStream())
                .createScoped(List.of(valueConfig.getGOOGLE_API_URI()));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
