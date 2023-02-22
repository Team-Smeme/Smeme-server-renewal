package com.smeme.server.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.smeme.server.config.jwt.JwtTokenProvider;
import com.smeme.server.config.jwt.UserAuthentication;
import com.smeme.server.dtos.auth.AuthSignInRequestDto;
import com.smeme.server.dtos.auth.AuthSignInResponseDto;
import com.smeme.server.models.Social;
import com.smeme.server.models.TargetLang;
import com.smeme.server.models.User;
import com.smeme.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Environment environment;
    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;
    @Transactional(readOnly = true)
    public AuthSignInResponseDto signIn(AuthSignInRequestDto dto, String socialAccessToken) {
        String socialId = String.valueOf(getKakaoUserData(socialAccessToken));

        if (userRepository.existsBySocialId(socialId)) {
            boolean isRegistered = true;
            Long userId = userRepository.findIdBySocialId(socialId);
            Authentication authentication = new UserAuthentication(userId, null, null);
            String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
            String accessToken = jwtTokenProvider.generateAccessToken(authentication);

            return new AuthSignInResponseDto(accessToken, refreshToken, isRegistered);
        }

        boolean isRegistered = false;

        User user = User.
                builder().
                social(Social.kakao)
                .socialId(socialId)
                .targetLang(TargetLang.en)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        User newUser = userRepository.findBySocialAndSocialId(Social.kakao, socialId);
        Authentication authentication = new UserAuthentication(newUser.getId(), null, null);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        newUser.updateRefreshToken(refreshToken);
        userRepository.save(newUser);

        return new AuthSignInResponseDto(accessToken, refreshToken, isRegistered);
    }

    private Long getKakaoUserData(String socialAccessToken)  {
        RestTemplate restTemplate = new RestTemplate();

        String kakaoUrl = environment.getProperty("jwt.KAKAO_URL");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", socialAccessToken);

            HttpEntity<JsonArray> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<Object> responseData = restTemplate.exchange(
                     kakaoUrl,
                    HttpMethod.GET,
                    httpEntity,
                    Object.class
            );

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.convertValue(responseData.getBody(), Map.class);
            Long id = (Long) map.get("id");
            return id;
        } catch (HttpClientErrorException e ) {
            throw e;
    }
}}
