package com.smeme.server.service;

import com.smeme.server.config.ValueConfig;
import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.dto.member.*;
import com.smeme.server.dto.training.TrainingTimeResponseDTO;
import com.smeme.server.model.Member;
import com.smeme.server.model.badge.Badge;
import com.smeme.server.model.badge.MemberBadge;
import com.smeme.server.model.goal.Goal;
import com.smeme.server.model.goal.GoalType;
import com.smeme.server.model.training.DayType;
import com.smeme.server.model.training.TrainingTime;
import com.smeme.server.repository.badge.BadgeRepository;
import com.smeme.server.repository.badge.MemberBadgeRepository;
import com.smeme.server.repository.MemberRepository;
import com.smeme.server.repository.goal.GoalRepository;
import com.smeme.server.repository.trainingTime.TrainingTimeRepository;
import com.smeme.server.util.message.ErrorMessage;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TrainingTimeRepository trainingTimeRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final GoalRepository goalRepository;
    private final BadgeRepository badgeRepository;
    private final ValueConfig valueConfig;

    @Transactional
    public MemberUpdateResponseDTO updateMember(Long memberId, MemberUpdateRequestDTO request) {
        checkMemberDuplicate(request.username());
        Member member = getMemberById(memberId);

        if (nonNull(request.termAccepted())) {
            member.updateTermAccepted(request.termAccepted());
        }

        ArrayList<Badge> badges = new ArrayList<>();
        if (isNull(member.getUsername())) {
            Badge welcomeBadge = getBadgeById(valueConfig.getWELCOME_BADGE_ID());
            memberBadgeRepository.save(new MemberBadge(member, welcomeBadge));
            badges.add(welcomeBadge);
        }
        member.updateUsername(request.username());
        return MemberUpdateResponseDTO.of(badges);
    }

    public MemberGetResponseDTO getMember(Long memberId) {
        Member member = getMemberById(memberId);
        Goal goal = getGoal(member.getGoal());
        List<TrainingTime> trainingTimeList = getTrainingTimeByMemberId(memberId);

        // TODO: beta test이후 리팩토링
        if( getTrainingTimeByMemberId(memberId).isEmpty()) {
            TrainingTimeResponseDTO trainingTimeResponseDTO = TrainingTimeResponseDTO.builder()
                    .day("")
                    .hour(22)
                    .minute(0)
                    .build();
            return MemberGetResponseDTO.of(goal, member, trainingTimeResponseDTO, BadgeResponseDTO.of(getBadge(memberId)));
        }

        TrainingTime trainingTime = getOneTrainingTime(getTrainingTimeByMemberId(memberId));
        TrainingTimeResponseDTO trainingTimeResponseDTO = TrainingTimeResponseDTO.builder()
                                                            .day(getDays(trainingTimeList))
                                                            .hour(trainingTime.getHour())
                                                            .minute(trainingTime.getMinute())
                                                            .build();
        return MemberGetResponseDTO.of(goal, member,trainingTimeResponseDTO ,BadgeResponseDTO.of(getBadge(memberId)));
    }


    @Transactional
    public void updateMemberPlan(Long memberId, MemberPlanUpdateRequestDTO request) {
        Member member = getMemberById(memberId);

        if (nonNull(request.target())) {
            member.updateGoal(request.target());
        }

        if (nonNull(request.hasAlarm())) {
            member.updateHasAlarm(request.hasAlarm());
        }

        if (nonNull(request.trainingTime()) && StringUtils.hasText(request.trainingTime().day())) {
            updateMemberTrainingTime(member, request);
        }
    }

    @Transactional
    public void updateMemberPush(Long memberId, MemberPushUpdateRequestDTO request) {
        Member member = getMemberById(memberId);
        member.updateHasAlarm(request.hasAlarm());
    }

    public MemberNameResponseDTO checkDuplicatedName(String name) {
        boolean isExist = memberRepository.existsByUsername(name);
        return new MemberNameResponseDTO(isExist);
    }

    private void updateMemberTrainingTime(Member member, MemberPlanUpdateRequestDTO request) {
        trainingTimeRepository.deleteAll(member.getTrainingTimes());
        for (String day : parseDay(request.trainingTime().day())) {
            TrainingTime trainingTime = TrainingTime.builder()
                    .day(DayType.valueOf(day))
                    .hour(request.trainingTime().hour())
                    .minute(request.trainingTime().minute())
                    .member(member)
                    .build();
            trainingTimeRepository.save(trainingTime);
        }
    }

    private String[] parseDay(String day) {
        return day.split(",");
    }


    private Goal getGoal(GoalType goalType) {
        return goalRepository.findOneByType(goalType).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.EMPTY_GOAL.getMessage()));
    }

    private Badge getBadge(Long memberId) {
        return memberBadgeRepository.findFirstByMemberIdOrderByCreatedAtDesc(memberId).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.EMPTY_BADGE.getMessage())).getBadge();
    }

    private TrainingTime getOneTrainingTime(List<TrainingTime> trainingTimeList) {
        return trainingTimeList.stream().findFirst().orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.EMPTY_TRAINING_TIME.getMessage()));
    }

    private String getDays(List<TrainingTime> trainingTimeList) {
        return trainingTimeList.stream()
                .map(trainingTime -> trainingTime.getDay().name())
                .distinct()
                .collect(Collectors.joining(","));
    }

    private List<TrainingTime> getTrainingTimeByMemberId(Long memberId) {
        return trainingTimeRepository.findAllByMemberId(memberId);
    }

    private Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.EMPTY_MEMBER.getMessage()));
    }
    private void checkMemberDuplicate(String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new EntityExistsException(ErrorMessage.DUPLICATE_USERNAME.getMessage());
        }
    }

    private Badge getBadgeById(Long id) {
        return badgeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.EMPTY_BADGE.getMessage())
        );
    }
}