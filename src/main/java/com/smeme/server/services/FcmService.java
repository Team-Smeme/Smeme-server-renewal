package com.smeme.server.services;

import static org.springframework.http.HttpHeaders.*;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.smeme.server.dtos.fcm.MessageDto;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

@Service
@RequiredArgsConstructor
public class FcmService {

	private final ObjectMapper objectMapper;
	private String FIREBASE_CONFIG_PATH = "test02.json";
	@Value("${api.url}")
	private String FIREBASE_ALARM_SEND_API_URI;

	/**
	 * 알림 푸쉬를 보내는 역할을 하는 메서드
	 * @param targetToken : 푸쉬 알림을 받을 클라이언트 앱의 식별 토큰
	 * */
	public void sendMessageTo(String targetToken, String title, String body) throws IOException {

		String message = makeMessage(targetToken, title, body);
		OkHttpClient client = new OkHttpClient();
		RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

		Request request = new Request.Builder()
			.url(FIREBASE_ALARM_SEND_API_URI)
			.post(requestBody)
			.addHeader(AUTHORIZATION, "Bearer " + getAccessToken())
			.addHeader(CONTENT_TYPE, "application/json; UTF-8")
			.build();

		client.newCall(request).execute();
	}

	/**
	 * makeMessage : 알림 파라미터들을 FCM이 요구하는 body 형태로 가공한다.
	 * @param targetToken : firebase token
	 * @param title : 알림 제목
	 * @param body : 알림 내용
	 * */
	private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
		MessageDto message = MessageDto.of(targetToken, title, body);
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
