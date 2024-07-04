package com.smeem.application.domain.member;

import com.smeem.application.domain.badge.Badge;
import com.smeem.application.port.input.MemberUseCase;
import com.smeem.application.port.input.dto.request.member.UpdateMemberHasPushAlarmRequest;
import com.smeem.application.port.input.dto.request.member.UpdateMemberRequest;
import com.smeem.application.port.input.dto.response.member.RetrieveMemberResponse;
import com.smeem.application.port.input.dto.response.member.RetrievePerformanceResponse;
import com.smeem.application.port.input.dto.response.member.UpdateMemberResponse;
import com.smeem.application.port.input.dto.response.member.UsernameDuplicatedResponse;
import com.smeem.application.port.output.external.NoticeUseCase;
import com.smeem.application.port.output.persistence.BadgePort;
import com.smeem.application.port.output.persistence.MemberPort;
import com.smeem.application.port.output.persistence.TrainingTimePort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
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
    private final NoticeUseCase noticeUseCase;

    @Transactional
    public UpdateMemberResponse updateMember(long memberId, UpdateMemberRequest request) {
        if (memberPort.isExistByUsername(request.username())) {
            throw new SmeemException(ExceptionCode.BAD_REQUEST, "Duplicated username: "+ request.username());
        }
        val foundMember = memberPort.findById(memberId);
        val acquiredBadges = new ArrayList<Badge>();
        if (foundMember.getUsername() == null) {
            noticeUseCase.noticeSignIn(request.username(), memberPort.countAll());
            acquiredBadges.add(badgePort.saveWelcomeBadge(memberId));
        }
        memberPort.update(request.updateMember(foundMember));
        return UpdateMemberResponse.of(acquiredBadges);
    }

    public RetrieveMemberResponse retrieveMember(long memberId) {
        val member = memberPort.findByIdJoinGoalAndPlan(memberId);
        val trainingTimes = trainingTimePort.findByMemberId(memberId);
        val recentMemberBadge = badgePort.findByMemberIdRecently(memberId);
        return RetrieveMemberResponse.of(member, trainingTimes, recentMemberBadge.orElse(null));
    }

    public UsernameDuplicatedResponse checkUsernameDuplicated(String username) {
        return UsernameDuplicatedResponse.of(memberPort.isExistByUsername(username));
    }

    @Transactional
    public void updateMemberHasPush(long memberId, UpdateMemberHasPushAlarmRequest request) {

    }

    public RetrievePerformanceResponse retrieveMemberPerformance(long memberId) {
        return null;
    }

    @Transactional
    public void checkAttendance(long memberId) {

    }
}
