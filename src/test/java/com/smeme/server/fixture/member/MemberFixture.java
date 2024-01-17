package com.smeme.server.fixture.member;

import com.smeme.server.dto.member.*;
import com.smeme.server.fixture.badge.BadgeFixture;
import com.smeme.server.fixture.goal.GoalFixture;
import com.smeme.server.fixture.trainingtime.TrainingTimeFixture;
import com.smeme.server.model.LangType;
import com.smeme.server.model.Member;
import com.smeme.server.model.SocialType;
import com.smeme.server.model.goal.GoalType;
import lombok.val;

public class MemberFixture {

    private static final String MEMBER_USERNAME = "홍길동";

    private static final SocialType MEMBER_SOCIAL = SocialType.KAKAO;
    private static final String MEMBER_SOCIAL_ID = "123456789";
    private static final String MEMBER_FCM_TOKEN = "fcm_token";
    private static final LangType TARGET_LANG = LangType.en;
    private static final boolean HAS_ALARM = true;

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

    public static MemberPushUpdateRequestDTO createMemberPushUpdateRequestDTO() {
        return new MemberPushUpdateRequestDTO(HAS_ALARM);
    }
}