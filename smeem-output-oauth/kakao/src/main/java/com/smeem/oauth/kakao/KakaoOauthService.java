package com.smeem.oauth.kakao;

import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.oauth.kakao.config.KakaoProperties;
import com.smeem.oauth.kakao.dto.KakaoResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class KakaoOauthService {
    private final KakaoProperties kakaoProperties;

    public String getKakaoData(final String accessToken) {
        try {
            val restClient = RestClient.create();
            val response = restClient.get()
                    .uri(kakaoProperties.url())
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
