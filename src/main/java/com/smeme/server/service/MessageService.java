package com.smeme.server.service;

import static org.springframework.http.HttpHeaders.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.smeme.server.config.ValueConfig;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.auth.oauth2.GoogleCredentials;
import com.smeme.server.dto.message.MessageDTO;
import com.smeme.server.model.Member;
import com.smeme.server.repository.MemberRepository;
import com.smeme.server.repository.trainingTime.TrainingTimeRepository;
import com.smeme.server.util.message.ErrorMessage;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final ObjectMapper objectMapper;
    private final TrainingTimeRepository trainingTimeRepository;
    private final MemberRepository memberRepository;
    private final ValueConfig valueConfig;

    public void pushMessageForTrainingTime(LocalDateTime now, String title, String body) {
        trainingTimeRepository.getTrainingTimeForPushAlarm(now)
                .forEach(trainingTime -> pushMessage(trainingTime.getMember().getFcmToken(), title, body));
    }

    public void pushTest(String title, String body, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.INVALID_MEMBER.getMessage()));
        pushMessage(member.getFcmToken(), title, body);
    }

    private void pushMessage(String targetToken, String title, String body) {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        MessageDTO message = MessageDTO.of(targetToken, title, body);
        return objectMapper.writeValueAsString(message);
    }

    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(valueConfig.getFIREBASE_CONFIG_PATH()).getInputStream())
                .createScoped(List.of(valueConfig.getGOOGLE_API_URI()));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
