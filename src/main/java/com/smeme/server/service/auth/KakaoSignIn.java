package com.smeme.server.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class KakaoSignIn {

    private final Environment env;

    protected String getKakaoData(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", accessToken);

            HttpEntity<JsonArray> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<Object> responseData = restTemplate.postForEntity(env.getProperty("jwt.KAKAO_URL"), httpEntity, Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.convertValue(responseData.getBody(), Map.class);

            String socialId = map.get("id").toString();

            return socialId;

        } catch (HttpClientErrorException e) {
            throw e;
        }
    }
}