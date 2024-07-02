package com.smeem.application.domain.member;

import com.smeem.application.domain.generic.LangType;
import com.smeem.application.domain.goal.Goal;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {
    private Long id;
    private final Social social;
    private String fcmToken;
    private String refreshToken;
    private String username;
    private Goal goal;
    private boolean hasPushAlarm;
    private boolean termAccepted;
    private LangType targetLang;
    private int diaryComboCount;

    public Member(SocialType socialType, String socialId, String fcmToken) {
        this.social = new Social(socialType, socialId);
        this.fcmToken = fcmToken;
        init();
    }

    public Member updateSmeemToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public Member emptyRefreshToken() {
        this.refreshToken = null;
        return this;
    }

    public Member updateDiaryComboCount(boolean diaryWrittenYesterday) {
        if (diaryWrittenYesterday) {
            diaryComboCount++;
        } else {
            diaryComboCount = 1;
        }
        return this;
    }

    private void init() {
        this.hasPushAlarm = false;
        this.termAccepted = false;
        this.targetLang = LangType.defaultType();
        this.diaryComboCount = 0;
    }

    public record Social(
            SocialType socialType,
            String socialId
    ) {
    }
}
