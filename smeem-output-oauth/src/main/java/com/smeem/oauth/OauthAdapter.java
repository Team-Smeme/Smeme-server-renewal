package com.smeem.oauth;

import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.member.SocialType;
import com.smeem.application.port.output.oauth.OauthPort;
import com.smeem.oauth.apple.AppleOauthService;
import com.smeem.oauth.kakao.KakaoOauthService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthAdapter implements OauthPort {
    private AppleOauthService appleOauthService;
    private KakaoOauthService kakaoOauthService;

    @Override
    public Member.Social login(SocialType socialType, String accessToken) {
        val socialId = getSocialId(socialType, accessToken);
        return new Member.Social(socialType, socialId);
    }

    private String getSocialId(SocialType socialType, String socialAccessToken) {
        return switch (socialType) {
            case APPLE -> appleOauthService.getAppleData(socialAccessToken);
            case KAKAO -> kakaoOauthService.getKakaoData(socialAccessToken);
        };
    }
}
