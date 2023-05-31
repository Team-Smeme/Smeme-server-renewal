package com.smeme.server.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class KakaoSignInService {

    @Value("${jwt.KAKAO_URL}")
    private String KAKAO_URL;

    protected String getKakaoData(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<JsonArray> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> responseData = restTemplate.postForEntity(KAKAO_URL, httpEntity, Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(responseData.getBody(), Map.class).get("id").toString();
    }
}