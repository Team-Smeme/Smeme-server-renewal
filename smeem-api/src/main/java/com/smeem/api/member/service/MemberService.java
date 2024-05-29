package com.smeem.api.member.service;

import com.smeem.api.badge.service.dto.response.BadgeServiceResponse;
import com.smeem.api.goal.service.GoalService;
import com.smeem.api.goal.service.dto.request.GoalGetServiceRequest;
import com.smeem.api.member.service.dto.request.*;
import com.smeem.api.member.service.dto.response.*;
import com.smeem.common.config.ValueConfig;
import com.smeem.domain.badge.adapter.BadgeFinder;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.member.adapter.member.MemberCounter;
import com.smeem.domain.member.adapter.member.MemberFinder;
import com.smeem.domain.member.adapter.member.MemberUpdater;
import com.smeem.domain.member.exception.MemberException;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.plan.adapter.PlanFinder;
import com.smeem.domain.training.model.DayType;
import com.smeem.domain.training.model.TrainingTime;
import com.smeem.domain.visit.adapter.VisitCounter;
import com.smeem.domain.visit.adapter.VisitFinder;
import com.smeem.domain.visit.adapter.VisitSaver;
import com.smeem.external.discord.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.smeem.common.code.failure.MemberFailureCode.DUPLICATE_USERNAME;
import static com.smeem.common.config.ValueConfig.SIGN_IN_MESSAGE;
import static com.smeem.external.discord.DiscordAlarmCase.INFO;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final BadgeFinder badgeFinder;
    private final MemberFinder memberFinder;
    private final MemberUpdater memberUpdater;
    private final PlanFinder planFinder;
    private final VisitSaver visitSaver;
    private final VisitFinder visitFinder;
    private final VisitCounter visitorCounter;

    private final TrainingTimeService trainingTimeService;
    private final GoalService goalService;
    private final MemberBadgeService memberBadgeService;
    private final AlarmService alarmService;
    private final ValueConfig valueConfig;
    private final MemberCounter memberCounter;
    private final VisitCounter visitCounter;

    @Transactional
    public MemberUpdateServiceResponse updateUserProfile(final MemberServiceUpdateUserProfileRequest request) {
        checkMemberDuplicate(request.username());
        val member = memberFinder.findById(request.memberId());
        updateTermAccepted(member, request);

        ArrayList<Badge> badges = new ArrayList<>();
        if (isNewMember(member)) {
            val allMemberCount = memberCounter.count();
            addWelcomeBadge(member, badges);
            alarmService.send(String.format(SIGN_IN_MESSAGE, request.username(), allMemberCount), INFO);
        }
        member.updateUsername(request.username());
        return MemberUpdateServiceResponse.of(badges);
    }

    public MemberGetServiceResponse getMemberProfile(final long memberId) {
        val member = memberFinder.findById(memberId);
        val goal = goalService.getByType(GoalGetServiceRequest.of(member.getGoal()));
        val badge = BadgeServiceResponse.of(memberBadgeService.getBadgeByMemberId(memberId));
        return MemberGetServiceResponse.of(goal, member, badge);
    }

    @Transactional
    public void updateLearningPlan(final MemberUpdatePlanServiceRequest request) {
        val member = memberFinder.findById(request.memberId());
        member.updateGoal(request.goalType());
        member.updateHasAlarm(request.hasAlarm());
        updateTrainingTime(member, request.trainingTime());
        updatePlan(member, request.planId());
    }

    @Transactional
    public void updateHasAlarm(final MemberPushUpdateServiceRequest request) {
        val member = memberFinder.findById(request.memberId());
        member.updateHasAlarm(request.hasAlarm());
    }

    public MemberNameServiceResponse checkDuplicatedName(final String name) {
        val isExist = memberFinder.existsByUsername(name);
        return MemberNameServiceResponse.of(isExist);
    }

    public MemberPerformanceGetServiceResponse getPerformanceSummary(final MemberPerformanceGetServiceRequest request) {
        val member = memberFinder.findById(request.memberId());
        val memberVisitCount = visitCounter.countByMember(member);
        return MemberPerformanceGetServiceResponse.of(member, memberVisitCount);
    }

    @Transactional
    public void updateMemberVisit(final MemberVisitUpdateRequest request) {
        val member = memberFinder.findById(request.memberId());
        if (!visitFinder.isVisitedToday(member)) {
            visitSaver.saveByMember(member);
        }
    }

    public Optional<MemberPlanGetServiceResponse> getMemberPlan(final MemberPlanGetServiceRequest request) {
        val member = memberFinder.findById(request.memberId());
        return Objects.nonNull(member.getPlan())
                ? Optional.of(MemberPlanGetServiceResponse.of(member))
                : Optional.empty();
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

    private void updatePlan(final Member member, final Long planId) {
        if (nonNull(planId)) {
            val plan = planFinder.findById(planId);
            member.updatePlan(plan);
        }
    }

    private String[] parseDay(final String day) {
        return day.split(",");
    }


    private void checkMemberDuplicate(final String username) {
        if (memberFinder.existsByUsername(username)) {
            throw new MemberException(DUPLICATE_USERNAME);
        }
    }

    private void addWelcomeBadge(final Member member, List<Badge> badges) {
        Badge welcomeBadge = badgeFinder.findById(valueConfig.getWELCOME_BADGE_ID());
        memberBadgeService.save(member, welcomeBadge);
        badges.add(welcomeBadge);
    }

    private boolean isNewMember(Member member) {
        return isNull(member.getUsername());
    }

    private void updateTermAccepted(final Member member, final MemberServiceUpdateUserProfileRequest request) {
        if (nonNull(request.termAccepted())) {
            memberUpdater.updateTermAccepted(member, request.termAccepted());
        }
    }
}
