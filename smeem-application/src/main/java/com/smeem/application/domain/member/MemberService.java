package com.smeem.application.domain.member;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.trainingtime.DayType;
import com.smeem.application.domain.trainingtime.TrainingTime;
import com.smeem.application.port.input.MemberUseCase;
import com.smeem.application.port.input.dto.request.member.UpdateMemberHasPushAlarmRequest;
import com.smeem.application.port.input.dto.request.member.UpdateMemberRequest;
import com.smeem.application.port.input.dto.request.plan.UpdateMemberPlanRequest;
import com.smeem.application.port.input.dto.response.member.RetrieveMemberResponse;
import com.smeem.application.port.input.dto.response.member.RetrievePerformanceResponse;
import com.smeem.application.port.input.dto.response.member.UpdateMemberResponse;
import com.smeem.application.port.input.dto.response.member.UsernameDuplicatedResponse;
import com.smeem.application.port.input.dto.response.plan.RetrieveMemberPlanResponse;
import com.smeem.application.port.output.cache.CachePort;
import com.smeem.application.port.output.persistence.*;
import com.smeem.common.logger.HookLogger;
import com.smeem.common.logger.LoggingMessage;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements MemberUseCase {
    private final MemberPort memberPort;
    private final TrainingTimePort trainingTimePort;
    private final BadgePort badgePort;
    private final DiaryPort diaryPort;
    private final GoalPort goalPort;
    private final PlanPort planPort;
    private final HookLogger hookLogger;
    private final CachePort cachePort;

    @Transactional
    public UpdateMemberResponse updateMember(long memberId, UpdateMemberRequest request) {
        val foundMember = memberPort.findById(memberId);
        val acquiredBadges = new ArrayList<Badge>();

        if (foundMember.signUp()) {
            acquiredBadges.add(badgePort.saveWelcomeBadgeToMember(foundMember.getId()));
            hookLogger.send(LoggingMessage.signIn(request.username(), memberPort.countAll()));
        }

        if (request.username() != null) {
            foundMember.updateUsername(request.username());
        }
        if (request.termAccepted() != null) {
            foundMember.updateTermAccepted(request.termAccepted());
        }
        memberPort.update(foundMember);

        return UpdateMemberResponse.of(acquiredBadges);
    }

    public RetrieveMemberResponse retrieveMember(long memberId) {
        val member = memberPort.findById(memberId);
        val trainingTimes = trainingTimePort.findByMemberId(memberId);
        val recentMemberBadge = badgePort.findRecentlyByMember(memberId);
        return RetrieveMemberResponse.of(
                member,
                member.getGoalId() != null ? goalPort.findById(member.getGoalId()) : null,
                member.getPlanId() != null ? planPort.findById(member.getPlanId()) : null,
                trainingTimes,
                recentMemberBadge.orElse(null));
    }

    public UsernameDuplicatedResponse checkUsernameDuplicated(String username) {
        return UsernameDuplicatedResponse.of(memberPort.isExistByUsername(username));
    }

    @Transactional
    public void updateMemberHasPush(long memberId, UpdateMemberHasPushAlarmRequest request) {
        val foundMember = memberPort.findById(memberId);
        foundMember.updateHasPushAlarm(request.hasAlarm());
        memberPort.update(foundMember);
    }

    public RetrievePerformanceResponse retrieveMemberPerformance(long memberId) {
        val foundMember = memberPort.findById(memberId);
        return RetrievePerformanceResponse.of(
                foundMember,
                diaryPort.countByMember(memberId),
                badgePort.countByMember(memberId));
    }

    @Transactional
    public void visit(long memberId) {
        Member foundMember = memberPort.findById(memberId);
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String key = "visit:" + today;

        if (!cachePort.getBit(key, foundMember.getId())) {
            System.out.println("test");
            foundMember.visit();
            memberPort.update(foundMember);
            cachePort.setBit(key, foundMember.getId(), true);
        }
    }

    @Transactional
    public void updatePlan(long memberId, UpdateMemberPlanRequest request) {
        val foundMember = memberPort.findById(memberId);

        if (request.target() != null) {
            val foundGoal = goalPort.findByGoalType(request.target());
            foundMember.updateGoal(foundGoal.getId());
        }
        if (request.hasAlarm() != null) {
            foundMember.updateHasPushAlarm(request.hasAlarm());
        }
        if (request.planId() != null) {
            foundMember.updatePlan(request.planId());
        }
        memberPort.update(foundMember);

        if (request.trainingTime() != null && !request.trainingTime().isEmpty()) {
            trainingTimePort.deleteByMemberId(memberId);
            val trainingTimes = new ArrayList<TrainingTime>();
            for (String dayType : request.trainingTime().day().split(",")) {
                trainingTimes.add(TrainingTime.of(
                        DayType.valueOf(dayType),
                        request.trainingTime().hour(),
                        request.trainingTime().minute(),
                        memberId));
            }
            trainingTimePort.saveAll(trainingTimes);
        }
    }

    public RetrieveMemberPlanResponse retrieveMemberPlan(long memberId) {
        val foundMember = memberPort.findById(memberId);
        val planId = foundMember.getPlanId();
        val foundPlan = planId != null ? planPort.findById(foundMember.getPlanId()) : null;
        val foundGoal = goalPort.findById(foundMember.getGoalId());
        return RetrieveMemberPlanResponse.of(
                foundGoal,
                foundPlan,
                diaryPort.countWeeklyByMember(foundMember.getId()));
    }
}
