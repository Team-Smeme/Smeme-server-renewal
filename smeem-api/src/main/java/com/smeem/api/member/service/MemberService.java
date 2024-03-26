package com.smeem.api.member.service;


import com.smeem.api.badge.service.BadgeService;
import com.smeem.api.badge.service.dto.response.BadgeServiceResponse;
import com.smeem.api.goal.service.GoalService;
import com.smeem.api.goal.service.dto.request.GoalGetServiceRequest;
import com.smeem.api.member.service.dto.request.MemberPushUpdateServiceRequest;
import com.smeem.api.member.service.dto.request.MemberServiceUpdateUserProfileRequest;
import com.smeem.api.member.service.dto.request.MemberUpdatePlanServiceRequest;
import com.smeem.api.member.service.dto.request.TrainingTimeServiceRequest;
import com.smeem.api.member.service.dto.response.MemberGetServiceResponse;
import com.smeem.api.member.service.dto.response.MemberNameServiceResponse;
import com.smeem.api.member.service.dto.response.MemberUpdateServiceResponse;
import com.smeem.api.member.service.dto.response.TrainingTimeServiceResponse;
import com.smeem.common.config.ValueConfig;
import com.smeem.domain.member.exception.MemberException;
import com.smeem.domain.training.exception.TrainingTimeException;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.domain.training.model.DayType;
import com.smeem.domain.training.model.TrainingTime;
import com.smeem.external.discord.DiscordAlarmSender;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.smeem.common.code.failure.MemberFailureCode.DUPLICATE_USERNAME;
import static com.smeem.common.code.failure.MemberFailureCode.EMPTY_MEMBER;
import static com.smeem.common.code.failure.TrainingTimeFailureCode.NOT_SET_TRAINING_TIME;
import static com.smeem.common.config.ValueConfig.SIGN_IN_MESSAGE;
import static com.smeem.external.discord.DiscordAlarmCase.INFO;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final TrainingTimeService trainingTimeService;
    private final GoalService goalService;
    private final BadgeService badgeService;
    private final MemberBadgeService memberBadgeService;
    private final DiscordAlarmSender discordAlarmSender;

    private final ValueConfig valueConfig;

    @Transactional
    public MemberUpdateServiceResponse updateUserProfile(final long memberId, final MemberServiceUpdateUserProfileRequest request) {
        checkMemberDuplicate(request.username());
        val member = get(memberId);
        updateTermAccepted(member, request);

        ArrayList<Badge> badges = new ArrayList<>();
        if (isNewMember(member)) {
            addWelcomeBadge(member, badges);
            discordAlarmSender.send(SIGN_IN_MESSAGE + member.getId(), INFO);
        }
        member.updateUsername(request.username());
        return MemberUpdateServiceResponse.of(badges);
    }

    public MemberGetServiceResponse getMemberProfile(final long memberId) {
        val member = get(memberId);
        val goal = goalService.getByType(GoalGetServiceRequest.of(member.getGoal()));
        val trainingTimes = trainingTimeService.getAllByMember(member);

        // 기본 시간 설정
        if (trainingTimes.isEmpty()) {
            val trainingTimeResponse = TrainingTimeServiceResponse.of("", 22, 0);
            val badgeResponse = BadgeServiceResponse.of(memberBadgeService.getBadgeByMemberId(memberId));
            return MemberGetServiceResponse.of(goal, member, trainingTimeResponse, badgeResponse);
        }

        return MemberGetServiceResponse.of(
                goal,
                member,
                generateTrainingTimeResponse(trainingTimes),
                BadgeServiceResponse.of(memberBadgeService.getBadgeByMemberId(memberId)));
    }

    @Transactional
    public void updateLearningPlan(final long memberId, final MemberUpdatePlanServiceRequest request) {
        val member = get(memberId);
        member.updateGoal(request.goalType());
        member.updateHasAlarm(request.hasAlarm());
        updateTrainingTime(member, request.trainingTime());
    }

    @Transactional
    public void updateHasAlarm(final long memberId, final MemberPushUpdateServiceRequest request) {
        val member = get(memberId);
        member.updateHasAlarm(request.hasAlarm());
    }

    public MemberNameServiceResponse checkDuplicatedName(final String name) {
        val isExist = memberRepository.existsByUsername(name);
        return MemberNameServiceResponse.of(isExist);
    }

    public Member get(final long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(EMPTY_MEMBER));
    }

    private void updateTrainingTime(Member member, TrainingTimeServiceRequest request) {
        if (nonNull(request) && StringUtils.hasText(request.day())) {
            trainingTimeService.deleteAll(member);
            for (String day : parseDay(request.day())) {
                val trainingTime = TrainingTime.builder()
                        .day(DayType.valueOf(day))
                        .hour(request.hour())
                        .minute(request.minute())
                        .member(member)
                        .build();
                trainingTimeService.save(trainingTime);
            }
        }
    }

    private String[] parseDay(final String day) {
        return day.split(",");
    }

    private TrainingTime getOneTrainingTime(List<TrainingTime> trainingTimes) {
        return trainingTimes.stream().findFirst().orElseThrow(
                () -> new TrainingTimeException(NOT_SET_TRAINING_TIME));
    }

    private String getDays(List<TrainingTime> trainingTimes) {
        return trainingTimes.stream()
                .map(trainingTime -> trainingTime.getDay().name())
                .distinct()
                .collect(Collectors.joining(","));
    }

    private void checkMemberDuplicate(final String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new MemberException(DUPLICATE_USERNAME);
        }
    }

    private void addWelcomeBadge(final Member member, List<Badge> badges) {
        Badge welcomeBadge = badgeService.get(valueConfig.getWELCOME_BADGE_ID());
        memberBadgeService.save(member, welcomeBadge);
        badges.add(welcomeBadge);
    }

    private TrainingTimeServiceResponse generateTrainingTimeResponse(List<TrainingTime> trainingTimes) {
        val trainingTime = getOneTrainingTime(trainingTimes);
        return TrainingTimeServiceResponse.of(
                getDays(trainingTimes),
                trainingTime.getHour(),
                trainingTime.getMinute());
    }

    private boolean isNewMember(Member member) {
        return isNull(member.getUsername());
    }

    private void updateTermAccepted(final Member member, final MemberServiceUpdateUserProfileRequest request) {
        if (nonNull(request.termAccepted())) {
            member.updateTermAccepted(request.termAccepted());
        }
    }
}