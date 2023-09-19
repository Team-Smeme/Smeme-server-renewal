package com.smeme.server.model;

import static com.smeme.server.util.Util.getStartOfDay;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.smeme.server.model.badge.MemberBadge;
import com.smeme.server.model.training.TrainingTime;
import com.smeme.server.model.goal.GoalType;

@Entity
@NoArgsConstructor
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 20, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SocialType social;

    @Column(nullable = false)
    private String socialId;

    private String refreshToken;

    @Enumerated(value = EnumType.STRING)
    private GoalType goal;

    private boolean hasPushAlarm;

    @Column(length = 10, unique = true)
    private String username;

    private String fcmToken;

    private boolean termAccepted;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LangType targetLang;

    private int diaryComboCount; //TODO: 논의

    @OneToMany(mappedBy = "member")
    private final List<TrainingTime> trainingTimes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<MemberBadge> badges = new ArrayList<>();

    @Builder
    public Member(SocialType social, String socialId, LangType targetLang, String fcmToken) {
        this.social = social;
        this.socialId = socialId;
        this.targetLang = targetLang;
        this.fcmToken = fcmToken;
        this.goal = null;
        this.diaryComboCount = 0; //TODO: 논의
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateTermAccepted(boolean termAccepted) {
        this.termAccepted = termAccepted;
    }

    public void updateHasAlarm(boolean hasAlarm) {
        this.hasPushAlarm = hasAlarm;
    }

    public void updateGoal(GoalType goal) {
        this.goal = goal;
    }

    public boolean isExistTodayDiary() {
        LocalDateTime now = getStartOfDay(LocalDateTime.now());
        return diaries.stream().anyMatch(diary -> getStartOfDay(diary.createdAt).equals(now));
    }

    //TODO: 논의
    public void updateDiaryCombo(boolean isCombo) {
        this.diaryComboCount = isCombo ? this.diaryComboCount + 1 : 1;
    }

}
