package com.smeem.application.domain.member;

import com.smeem.application.domain.generic.LangType;
import com.smeem.application.domain.goal.Goal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Member {
    private Long id;
    private final Social social;
    private String fcmToken;
    private String refreshToken;
    private String username;
    private Goal goal;
    private boolean hasPushAlarm = false;
    private boolean termAccepted = false;
    private LangType targetLang = LangType.defaultType();

    @Builder
    public Member(SocialType socialType, String socialId, String fcmToken) {
        this.social = new Social(socialType, socialId);
        this.fcmToken = fcmToken;
    }

    public Member updateSmeemToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public Member emptyRefreshToken() {
        this.refreshToken = null;
        return this;
    }

    public record Social(
            SocialType socialType,
            String socialId
    ) {
    }
}
