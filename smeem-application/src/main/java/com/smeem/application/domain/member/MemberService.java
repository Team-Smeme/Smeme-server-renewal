package com.smeem.application.domain.member;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.domain.trainingtime.DayType;
import com.smeem.application.domain.trainingtime.TrainingTime;
import com.smeem.application.domain.visit.Visit;
import com.smeem.application.port.input.MemberUseCase;
import com.smeem.application.port.input.dto.request.member.UpdateMemberHasPushAlarmRequest;
import com.smeem.application.port.input.dto.request.member.UpdateMemberRequest;
import com.smeem.application.port.input.dto.request.plan.UpdateMemberPlanRequest;
import com.smeem.application.port.input.dto.response.member.RetrieveMemberResponse;
import com.smeem.application.port.input.dto.response.member.RetrievePerformanceResponse;
import com.smeem.application.port.input.dto.response.member.UpdateMemberResponse;
import com.smeem.application.port.input.dto.response.member.UsernameDuplicatedResponse;
import com.smeem.application.port.input.dto.response.plan.RetrieveMemberPlanResponse;
import com.smeem.application.port.output.notice.NoticePort;
import com.smeem.application.port.output.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements MemberUseCase {
    private final MemberPort memberPort;
    private final TrainingTimePort trainingTimePort;
    private final BadgePort badgePort;
    private final DiaryPort diaryPort;
    private final VisitPort visitPort;
    private final NoticePort noticePort;
    private final GoalPort goalPort;
    private final PlanPort planPort;

    @Transactional
    public UpdateMemberResponse updateMember(long memberId, UpdateMemberRequest request) {
        val foundMember = memberPort.findById(memberId);
        val isSignIn = foundMember.getUsername() == null;
        val acquiredBadges = new ArrayList<Badge>();

        if (request.username() != null) {
            foundMember.updateUsername(request.username());
        }
        if (request.termAccepted() != null) {
            foundMember.updateTermAccepted(request.termAccepted());
        }
        memberPort.update(foundMember);

        if (isSignIn) {
            noticePort.noticeSignIn(request.username(), memberPort.countAll());
            acquiredBadges.add(badgePort.saveWelcomeBadgeToMember(memberId));
        }

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
    public void checkAttendance(long memberId) {
        val foundMember = memberPort.findById(memberId);
        if (!visitPort.isExistByMemberAndToday(foundMember.getId())) {
            foundMember.visit();
            visitPort.visit(new Visit(foundMember.getId()));
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
        if (request.trainingTime() != null) {
            trainingTimePort.deleteByMemberId(memberId);
            val trainingTimes = new ArrayList<TrainingTime>();
            for (String dayType : request.trainingTime().day().split(",")) {
                trainingTimes.add(new TrainingTime(
                        DayType.valueOf(dayType),
                        request.trainingTime().hour(),
                        request.trainingTime().minute()));
            }
            trainingTimePort.saveAll(trainingTimes);
        }
    }

    public RetrieveMemberPlanResponse retrieveMemberPlan(long memberId) {
        val foundMember = memberPort.findById(memberId);
        val foundPlan = planPort.findById(foundMember.getPlanId());
        val foundGoal = goalPort.findById(foundMember.getGoalId());
        return RetrieveMemberPlanResponse.of(
                foundGoal,
                foundPlan,
                diaryPort.countWeeklyByMember(foundMember.getId()));
    }
}
