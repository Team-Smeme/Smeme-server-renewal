package com.smeem.oauth.kakao;

import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.oauth.kakao.dto.KakaoResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Service
public class KakaoOauthService {
    @Value("${jwt.KAKAO_URL}")
    private String KAKAO_URL;

    public String getKakaoData(final String accessToken) {
        try {
            val restClient = RestClient.create();
            val response = restClient.get()
                    .uri(KAKAO_URL)
                    .header(AUTHORIZATION, accessToken)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            (kakaoRequest, kakaoResponse) -> {
                                throw new SmeemException(ExceptionCode.SERVICE_AVAILABLE);
                            })
                    .body(KakaoResponse.class);
            assert response != null;
            return response.id();
        } catch (RuntimeException exception) {
            throw new SmeemException(ExceptionCode.SERVICE_AVAILABLE);
        }
    }
}
