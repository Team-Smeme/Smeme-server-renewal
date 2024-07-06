package com.smeem.external.oauth;

import com.smeem.api.domain.auth.vo.Social;
import com.smeem.api.domain.auth.vo.SocialType;
import com.smeem.api.port.output.auth.SocialProvider;
import com.smeem.common.exception.SmeemException;
import com.smeem.external.oauth.apple.AppleService;
import com.smeem.external.oauth.kakao.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialLoginService implements SocialProvider {

    private final AppleService appleService;
    private final KakaoService kakaoService;

    @Override
    public Social login(SocialType socialType, String socialAccessToken) {
        val socialId = getSocialId(socialType, socialAccessToken);
        return Social.of(socialType, socialId);
    }

    private String getSocialId(SocialType socialType, String socialAccessToken) {
        return switch (socialType) {
            case APPLE -> appleService.getAppleData(socialAccessToken);
            case KAKAO -> kakaoService.getKakaoData(socialAccessToken);
            default -> throw new SmeemException(401, "소셜 로그인 실패: ${socialType}");
        };
    }
}
