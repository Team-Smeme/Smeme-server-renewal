package com.smeem.application.domain.member;

import com.smeem.application.domain.generic.LangType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Member {
    private Long id;
    private final Social social;
    private String fcmToken;
    private String refreshToken;
    private String username;
    private Long goalId;
    private Long planId;
    private boolean hasPushAlarm;
    private boolean termAccepted;
    private LangType targetLang;
    private int diaryComboCount;
    private int visitDays;

    public Member(SocialType socialType, String socialId, String fcmToken) {
        this.social = new Social(socialType, socialId);
        this.fcmToken = fcmToken;
        init();
    }

    private void init() {
        this.hasPushAlarm = false;
        this.termAccepted = false;
        this.targetLang = LangType.defaultType();
        this.diaryComboCount = 0;
    }

    public Member emptyRefreshToken() {
        this.refreshToken = null;
        return this;
    }

    public void visit() {
        this.visitDays++;
    }

    /**
     * update
     */
    public void updateTokenInLogin(String refreshToken, String fcmToken) {
        this.refreshToken = refreshToken;
        this.fcmToken = fcmToken;
    }

    public Member updateDiaryComboCount(boolean diaryWrittenYesterday) {
        if (diaryWrittenYesterday) {
            diaryComboCount++;
        } else {
            diaryComboCount = 1;
        }
        return this;
    }

    public void updateHasPushAlarm(boolean hasPushAlarm) {
        this.hasPushAlarm = hasPushAlarm;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateTermAccepted(boolean termAccepted) {
        this.termAccepted = termAccepted;
    }

    public void updateGoal(long goalId) {
        this.goalId = goalId;
    }

    public void updatePlan(long planId) {
        this.planId = planId;
    }

    /**
     * record
     */
    public record Social(
            SocialType socialType,
            String socialId
    ) {
    }
}
