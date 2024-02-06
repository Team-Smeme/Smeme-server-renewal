package com.smeem.api.fixture.member;

import com.smeem.api.fixture.goal.GoalFixture;
import com.smeem.api.fixture.badge.BadgeFixture;
import com.smeem.api.fixture.trainingtime.TrainingTimeFixture;
import com.smeem.api.member.controller.dto.request.MemberPlanUpdateRequestDTO;
import com.smeem.api.member.controller.dto.request.MemberPushUpdateRequestDTO;
import com.smeem.api.member.controller.dto.request.MemberUpdateRequestDTO;
import com.smeem.api.member.controller.dto.response.MemberGetResponseDTO;
import com.smeem.domain.goal.model.GoalType;
import com.smeem.domain.member.model.LangType;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.SocialType;
import lombok.val;

import static com.smeem.domain.goal.model.GoalType.DEVELOP;
import static com.smeem.domain.member.model.LangType.en;
import static com.smeem.domain.member.model.SocialType.KAKAO;

public class MemberFixture {

    private static final String MEMBER_USERNAME = "홍길동";

    private static final SocialType MEMBER_SOCIAL = KAKAO;
    private static final String MEMBER_SOCIAL_ID = "123456789";
    private static final String MEMBER_FCM_TOKEN = "fcm_token";
    private static final LangType TARGET_LANG = en;
    private static final boolean HAS_ALARM = true;

    public static Member createMember() {
        val member = Member.builder()
                .social(MEMBER_SOCIAL)
                .socialId(MEMBER_SOCIAL_ID)
                .targetLang(TARGET_LANG)
                .fcmToken(MEMBER_FCM_TOKEN)
                .build();
        member.updateGoal(DEVELOP);
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
                DEVELOP,
                TrainingTimeFixture.createTrainingTimeRequestDTO(),
                HAS_ALARM
        );
    }

    public static MemberPushUpdateRequestDTO createMemberPushUpdateRequestDTO() {
        return new MemberPushUpdateRequestDTO(HAS_ALARM);
    }
}