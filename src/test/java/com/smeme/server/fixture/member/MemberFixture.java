package com.smeme.server.fixture.member;

import com.smeme.server.dto.member.*;
import com.smeme.server.fixture.badge.BadgeFixture;
import com.smeme.server.fixture.goal.GoalFixture;
import com.smeme.server.fixture.trainingtime.TrainingTimeFixture;
import com.smeme.server.model.LangType;
import com.smeme.server.model.Member;
import com.smeme.server.model.SocialType;
import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.BadgeType;
import com.smeme.server.model.goal.GoalType;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

import static com.smeme.server.fixture.badge.BadgeFixture.createBadgeResponseDTO;
import static com.smeme.server.fixture.goal.GoalFixture.createGoalResponseDTO;

public class MemberFixture {

    private static final String MEMBER_USERNAME = "홍길동";

    private static final SocialType MEMBER_SOCIAL = SocialType.KAKAO;
    private static final String MEMBER_SOCIAL_ID = "123456789";
    private static final String MEMBER_FCM_TOKEN = "fcm_token";
    private static final LangType TARGET_LANG = LangType.en;
    private static final boolean MEMBER_IS_EXIST = true;


    private static final boolean HAS_ALARM = true;

    // Badge
    private static final String BADGE_NAME = "연속 3일 일기 뱃지";
    private static final Long BADGE_ID = 1L;
    private static final BadgeType BADGE_TYPE = BadgeType.COMBO;
    private static final String BADGE_IMAGE_URL = "https://m.s3.ap-northeast-2.amazonaws.com/badge/streak.png";

    public static Member createMember() {
        val member =  Member.builder()
                .social(MEMBER_SOCIAL)
                .socialId(MEMBER_SOCIAL_ID)
                .targetLang(TARGET_LANG)
                .fcmToken(MEMBER_FCM_TOKEN)
                .build();
        member.updateGoal(GoalType.DEVELOP);
        return member;
    }

    public static MemberGetResponseDTO createMemberGetResponseDTO() {
        return MemberGetResponseDTO.of(
                GoalFixture.createGoalResponseDTO(),
                createMember(),
                TrainingTimeFixture.createTrainingTimeResponseDTO(),
                BadgeFixture.createBadgeResponseDTO()
        );
    }

    public static MemberNameResponseDTO createMemberNameResponseDTO() {
        return new MemberNameResponseDTO(MEMBER_IS_EXIST);
    }

    public static MemberUpdateRequestDTO createMemberUpdateRequestDTO() {
        return new MemberUpdateRequestDTO(MEMBER_USERNAME, HAS_ALARM);
    }

    public static MemberPlanUpdateRequestDTO createMemberPlanUpdateRequestDTO() {
        return new MemberPlanUpdateRequestDTO(
                GoalType.DEVELOP,
                TrainingTimeFixture.createTrainingTimeRequestDTO(),
                HAS_ALARM
        );
    }

    public static MemberUpdateResponseDTO createMemberUpdateResponseDTO() {

        List<Badge> badges = new ArrayList<>();

        for (long l = 1L; l < 5L; l++) {
            Badge badge = Badge.builder()
                    .id(l)
                    .name(BADGE_NAME)
                    .type(BADGE_TYPE)
                    .imageUrl(BADGE_IMAGE_URL)
                    .build();
            badges.add(badge);
        }
        return MemberUpdateResponseDTO.of(badges);
    }

    public static MemberPushUpdateRequestDTO createMemberPushUpdateRequestDTO() {
        return new MemberPushUpdateRequestDTO(HAS_ALARM);
    }
}