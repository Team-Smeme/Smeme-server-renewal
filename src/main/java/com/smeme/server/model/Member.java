package com.smeme.server.model;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.smeme.server.model.badge.MemberBadge;
import com.smeme.server.model.training.TrainingTime;
import com.smeme.server.model.goal.GoalType;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id @GeneratedValue(strategy = IDENTITY)
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

    @Column(name="fcm_token")
    private String fcmToken;

    @Column(name="term_accepted")
    private boolean termAcceped;


    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LangType targetLang;

    @Column(name = "terms_agreed", nullable = false)
    private boolean termsAgreed;

    @OneToMany(mappedBy = "member")
    private final List<TrainingTime> trainingTimes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<MemberBadge> badges = new ArrayList<>();


    @Builder
    public Member(SocialType social, String socialId, LangType targetLang) {
        this.social = social;
        this.socialId = socialId;
        this.targetLang = targetLang;
        this.goal = null;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateUsername(String username) { this.username = username; }

    public void updateTermAccepted(boolean termAccepted) { this.termAcceped = termAccepted; }
}
