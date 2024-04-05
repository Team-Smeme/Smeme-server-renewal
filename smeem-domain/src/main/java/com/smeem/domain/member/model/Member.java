package com.smeem.domain.member.model;

import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.diary.model.Diary;
import com.smeem.domain.goal.model.GoalType;
import com.smeem.domain.common.BaseTimeEntity;
import com.smeem.domain.training.model.TrainingTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_plan_id")
    private MemberTrainingPlan trainingPlan;

    @OneToMany(mappedBy = "member")
    private final List<TrainingTime> trainingTimes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<MemberBadge> badges = new ArrayList<>();

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Builder
    public Member(SocialType social, String socialId, LangType targetLang, String fcmToken) {
        this.social = social;
        this.socialId = socialId;
        this.targetLang = targetLang;
        this.fcmToken = fcmToken;
        this.goal = null;
        this.diaryComboCount = 0;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateTermAccepted(boolean termAccepted) {
        this.termAccepted = termAccepted;
    }

    public void updateHasAlarm(Boolean hasAlarm) {
        if (Objects.nonNull(hasAlarm)) {
            this.hasPushAlarm = hasAlarm;
        }
    }

    public void updateGoal(GoalType goal) {
        if (Objects.nonNull(goal)) {
            this.goal = goal;
        }
    }

    public boolean hasDiaryWrittenToday() {
        return this.diaries.stream().anyMatch(Diary::isWrittenToday);
    }

    public void updateDiaryCombo(Diary diary) {
        this.diaryComboCount = diary.isCombo() ? this.diaryComboCount + 1 : 1;
    }

    public void updateDiaryCombo() {
        this.diaryComboCount = calculateDiaryComboCount();
    }

    private List<Diary> getDiariesNotWrittenTodayOrderByCreatedAtDesc() {
        return this.diaries.stream()
                .filter(diary -> !diary.isWrittenToday())
                .sorted(Comparator.comparing(Diary::getCreatedAt).reversed())
                .toList();
    }

    private int calculateDiaryComboCount() {
        val diaries = getDiariesNotWrittenTodayOrderByCreatedAtDesc();
        int count = hasDiaryWrittenToday() ? 1 : 0;
        LocalDate currentDate = LocalDate.now();

        for (Diary diary : diaries) {
            currentDate = currentDate.minusDays(1);
            if (!diary.isCreatedAt(currentDate)) {
                break;
            }
            count++;
        }

        return count;
    }

    public static Member createInitialMember(SocialType socialType, String socialId, String fcmToken) {
        return Member.builder()
                .social(socialType)
                .socialId(socialId)
                .targetLang(LangType.defaultLangType())
                .fcmToken(fcmToken)
                .build();
    }

    public boolean hasNotBadge(Badge badge) {
        return badges.stream().noneMatch(memberBadge -> memberBadge.getBadge().equals(badge));
    }

    public List<Diary> getDiariesBetweenDate(LocalDate startDate, LocalDate endDate) {
        return this.diaries.stream()
                .filter(diary -> diary.isBetween(startDate, endDate))
                .toList();
    }

    public boolean hasDiaryWrittenAgo(int duration) {
        val remindDate = LocalDate.now().minusDays(duration);
        return this.diaries.stream().anyMatch(diary -> diary.isCreatedAt(remindDate));
    }

}
