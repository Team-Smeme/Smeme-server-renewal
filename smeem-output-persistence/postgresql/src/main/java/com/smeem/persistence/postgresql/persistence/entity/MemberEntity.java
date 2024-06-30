package com.smeem.persistence.postgresql.persistence.entity;

import com.smeem.application.domain.generic.LangType;
import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.member.SocialType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

//    @Embedded
//    private DiaryComboInfo diaryComboInfo;

    private Long goalId;

    private Long trainingPlanId;

    public MemberEntity(Member member) {
        //TODO: fill
        socialId = member.getSocial().socialId();
        socialType = member.getSocial().socialType();
        refreshToken = member.getRefreshToken();
        fcmToken = member.getFcmToken();
        username = member.getUsername();
        hasPushAlarm = member.isHasPushAlarm();
        termAccepted = member.isTermAccepted();
        targetLang = member.getTargetLang();
    }

    public Member toDomain() {
        //TODO: fill
        return new Member(
                fcmToken,
                null,
                hasPushAlarm,
                id,
                refreshToken,
                new Member.Social(socialType, socialId),
                targetLang,
                termAccepted,
                username);
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
