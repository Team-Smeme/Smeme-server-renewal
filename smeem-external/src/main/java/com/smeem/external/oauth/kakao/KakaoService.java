package com.smeem.external.oauth.kakao;

import com.smeem.common.config.ValueConfig;
import com.smeem.common.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static com.smeem.common.code.failure.AuthFailureCode.FAIL_KAKAO_REQUEST;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Service
public class KakaoService {

    private final ValueConfig valueConfig;

    public String getKakaoData(final String accessToken) {
        try {
            RestClient restClient = RestClient.create();
            val response = restClient.get()
                    .uri(valueConfig.getKAKAO_URL())
                    .header(AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            (kakaoRequest, kakaoResponse) -> {
                            throw new AuthException(FAIL_KAKAO_REQUEST);
                    })
                    .body(KakaoResponse.class);
            assert response != null;
            return response.id();
        } catch (Exception e) {
            throw new AuthException(FAIL_KAKAO_REQUEST);
        }
    }
}
