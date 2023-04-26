package com.smeme.server.service;

import static org.springframework.http.HttpHeaders.*;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.smeme.server.dto.message.MessageDTO;
import com.smeme.server.dto.message.MessageRequestDTO;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

@Service
@RequiredArgsConstructor
public class MessageService {
	private final ObjectMapper objectMapper;

	@Value("${fcm.file_path}")
	private String FIREBASE_CONFIG_PATH;
	@Value("${fcm.url}")
	private String FIREBASE_API_URI;

	public void pushMessage(MessageRequestDTO requestDto) throws Exception {
		System.out.println(FIREBASE_API_URI);
		String message = makeMessage(requestDto);
		RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

		Request request = new Request.Builder()
			.url(FIREBASE_API_URI)
			.post(requestBody)
			.addHeader(AUTHORIZATION, "Bearer " + getAccessToken())
			.addHeader(CONTENT_TYPE, "application/json; UTF-8")
			.build();

		OkHttpClient client = new OkHttpClient();
		client.newCall(request).execute();
	}

	private String makeMessage(MessageRequestDTO requestDto) throws JsonProcessingException {
		MessageDTO message = MessageDTO.of(requestDto.targetToken(), requestDto.title(), requestDto.body());
		return objectMapper.writeValueAsString(message);
	}

	private String getAccessToken() throws IOException {
		GoogleCredentials googleCredentials = GoogleCredentials
			.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())
			.createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
		googleCredentials.refreshIfExpired();
		return googleCredentials.getAccessToken().getTokenValue();
	}
}
