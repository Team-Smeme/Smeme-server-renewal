package com.smeem.domain.member.model;

import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.diary.model.Diary;
import com.smeem.domain.goal.model.GoalType;
import com.smeem.domain.common.BaseTimeEntity;
import com.smeem.domain.plan.model.Plan;
import com.smeem.domain.training.model.TrainingTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

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

    @Embedded
    private DiaryComboInfo diaryComboInfo;

    @Embedded
    private MemberVisitInfo visitInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_plan_id")
    private Plan plan;

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
        this.diaryComboInfo = new DiaryComboInfo();
        this.visitInfo = new MemberVisitInfo();
    }

    public Member(Long id, SocialType social, String socialId, LangType targetLang) {
        this.id = id;
        this.social = social;
        this.socialId = socialId;
        this.targetLang = targetLang;
        this.diaryComboInfo = new DiaryComboInfo();
        this.visitInfo = new MemberVisitInfo();
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

    public void updateDiaryComboCount() {
        this.diaryComboInfo.update();
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
        return this.badges.stream().noneMatch(memberBadge -> memberBadge.getBadge().equals(badge));
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

    public boolean hasWriteDiaryToday() {
        val today = LocalDate.now();
        return this.diaries.stream().anyMatch(diary -> diary.getCreatedAt().toLocalDate().equals(today));
    }

    public int getVisitCount() {
        val visitCount = Objects.nonNull(this.visitInfo) ? this.visitInfo.getVisitCount() : null;
        return Objects.nonNull(visitCount) ? visitCount : 1;
    }

    public void updatePlan(Plan plan) {
        this.plan = plan;
    }

    public void updateVisitInfoToday() {
        if (isNull(this.visitInfo)) {
            this.visitInfo = new MemberVisitInfo();
        } else {
            this.visitInfo.updateToday();
        }
    }

    public int getDiaryCountInWeek() {
        return this.diaries.stream()
                .filter(diary -> isBetweenThisWeek(diary.getCreatedAt().toLocalDate()))
                .toList()
                .size();
    }

    public int getDiaryComboCount() {
        return this.diaryComboInfo.getDiaryComboCount();
    }

    private boolean isBetweenThisWeek(LocalDate date) {
        val today = LocalDate.now();
        val dayNum = today.getDayOfWeek().getValue();
        val monday = today.minusDays(dayNum - 1);
        val sunday = today.plusDays(7 - dayNum);
        return date.isAfter(monday.minusDays(1)) && date.isBefore(sunday.plusDays(1));
    }
}
