package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.generic.LangType;
import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.member.SocialType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
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
    private String refreshToken;
    private boolean hasPushAlarm;
    @Column(length = 10, unique = true)
    private String username;
    private String fcmToken;
    private boolean termAccepted;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LangType targetLang;
    private int diaryComboCount;

//    @Embedded
//    private DiaryComboInfo diaryComboInfo;

    private Long goalId;

    private Long trainingPlanId;

    public Member toDomain() {
        return Member.builder()
                .id(id)
                .social(new Member.Social(socialType, socialId))
                .fcmToken(fcmToken)
                .refreshToken(refreshToken)
                .username(username)
                .hasPushAlarm(hasPushAlarm)
                .termAccepted(termAccepted)
                .targetLang(targetLang)
                .diaryComboCount(diaryComboCount)
                .build();
    }

    public static MemberEntity of(Member member) {
        return MemberEntity.builder()
                .socialId(member.getSocial().socialId())
                .socialType(member.getSocial().socialType())
                .refreshToken(member.getRefreshToken())
                .hasPushAlarm(member.isHasPushAlarm())
                .termAccepted(member.isTermAccepted())
                .targetLang(member.getTargetLang())
                .diaryComboCount(member.getDiaryComboCount())
                .username(member.getUsername())
                .fcmToken(member.getFcmToken())
                .build();

    }

    public void update(Member member) {
        refreshToken = member.getRefreshToken();
        fcmToken = member.getFcmToken();
        username = member.getUsername();
        hasPushAlarm = member.isHasPushAlarm();
        termAccepted = member.isTermAccepted();
        targetLang = member.getTargetLang();
    }
}
