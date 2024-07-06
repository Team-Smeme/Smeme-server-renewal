package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.generic.LangType;
import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.member.SocialType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member", schema = "smeem") // TODO: 통일
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;
    @Column(nullable = false)
    private String socialId;
    private String fcmToken;
    private String refreshToken;
    @Column(length = 10, unique = true)
    private String username;
    private Long goalId;
    private Long planId;
    private boolean hasPushAlarm;
    private boolean termAccepted;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LangType targetLang;
    private int diaryComboCount;
    private int visitDays;

    public Member toDomain() {
        return Member.builder()
                .id(id)
                .social(new Member.Social(socialType, socialId))
                .fcmToken(fcmToken)
                .refreshToken(refreshToken)
                .username(username)
                .goalId(goalId)
                .planId(planId)
                .hasPushAlarm(hasPushAlarm)
                .termAccepted(termAccepted)
                .targetLang(targetLang)
                .diaryComboCount(diaryComboCount)
                .visitDays(visitDays)
                .build();
    }

    public static MemberEntity of(Member member) {
        return MemberEntity.builder()
                .socialId(member.getSocial().socialId())
                .socialType(member.getSocial().socialType())
                .fcmToken(member.getFcmToken())
                .refreshToken(member.getRefreshToken())
                .username(member.getUsername())
                .goalId(member.getGoalId())
                .planId(member.getPlanId())
                .hasPushAlarm(member.isHasPushAlarm())
                .termAccepted(member.isTermAccepted())
                .targetLang(member.getTargetLang())
                .diaryComboCount(member.getDiaryComboCount())
                .visitDays(member.getVisitDays())
                .build();
    }

    public void update(Member member) {
        fcmToken = member.getFcmToken();
        refreshToken = member.getRefreshToken();
        username = member.getUsername();
        goalId = member.getGoalId();
        planId = member.getPlanId();
        hasPushAlarm = member.isHasPushAlarm();
        termAccepted = member.isTermAccepted();
        targetLang = member.getTargetLang();
        diaryComboCount = member.getDiaryComboCount();
        visitDays = member.getVisitDays();
    }
}
