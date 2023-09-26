package com.smeme.server.model;

import static jakarta.persistence.GenerationType.*;
import static java.time.LocalDateTime.now;

import com.smeme.server.model.badge.Badge;
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

    private int diaryComboCount;

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
        this.diaryComboCount = 0;
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

    public boolean wroteDiaryToday() {
        return this.diaries.stream()
                .filter(Diary::isValid)
                .anyMatch(diary -> diary.isCreatedAt(now()));
    }

    public void updateDiaryCombo(boolean isCombo) {
        this.diaryComboCount = isCombo ? this.diaryComboCount + 1 : 1;
    }

    public void updateDiaryCombo() {
        List<Diary> diaries = this.diaries.stream()
                .filter(diary -> diary.isValid() && !diary.isCreatedAt(now()))
                .sorted((a, b) -> b.createdAt.compareTo(a.createdAt)).toList();

        int count = wroteDiaryToday() ? 1 : 0;
        LocalDateTime day = now();

        for (Diary diary : diaries) {
            day = day.minusDays(1);
            if (!diary.isCreatedAt(day)) {
                break;
            }
            count++;
        }

        this.diaryComboCount = count;
    }

    public boolean hasNotBadge(Badge badge) {
        return badges.stream().noneMatch(memberBadge -> memberBadge.getBadge().equals(badge));
    }

}
