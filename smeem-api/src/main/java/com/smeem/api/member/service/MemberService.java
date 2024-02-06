package com.smeem.api.member.service;


import com.smeem.api.badge.controller.dto.response.BadgeResponseDTO;
import com.smeem.api.badge.service.BadgeService;
import com.smeem.api.goal.controller.dto.response.GoalResponseDTO;
import com.smeem.api.goal.service.GoalService;
import com.smeem.api.member.controller.dto.request.MemberPlanUpdateRequestDTO;
import com.smeem.api.member.controller.dto.request.MemberPushUpdateRequestDTO;
import com.smeem.api.member.controller.dto.request.MemberUpdateRequestDTO;
import com.smeem.api.member.controller.dto.response.MemberGetResponseDTO;
import com.smeem.api.member.controller.dto.response.MemberNameResponseDTO;
import com.smeem.api.member.controller.dto.response.MemberUpdateResponseDTO;
import com.smeem.api.member.controller.dto.response.TrainingTimeResponseDTO;
import com.smeem.common.code.ErrorMessage;
import com.smeem.common.config.ValueConfig;
import com.smeem.domain.badge.model.Badge;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.domain.training.model.DayType;
import com.smeem.domain.training.model.TrainingTime;
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

    private final TrainingTimeService trainingTimeService;
    private final GoalService goalService;
    private final BadgeService badgeService;
    private final MemberBadgeService memberBadgeService;

    private final ValueConfig valueConfig;

    @Transactional
    public MemberUpdateResponseDTO updateMember(Long memberId, MemberUpdateRequestDTO request) {
        checkMemberDuplicate(request.username());
        Member member = get(memberId);

        if (nonNull(request.termAccepted())) {
            member.updateTermAccepted(request.termAccepted());
        }

        ArrayList<Badge> badges = new ArrayList<>();
        if (isNull(member.getUsername())) {
            Badge welcomeBadge = badgeService.get(valueConfig.getWELCOME_BADGE_ID());
            memberBadgeService.save(member, welcomeBadge);
            badges.add(welcomeBadge);
        }
        member.updateUsername(request.username());
        return MemberUpdateResponseDTO.of(badges);
    }

    public MemberGetResponseDTO getProfile(Long memberId) {
        Member member = get(memberId);
        GoalResponseDTO goal = goalService.getByType(member.getGoal());
        List<TrainingTime> trainingTimes = trainingTimeService.getAllByMember(member);

        // 기본 시간 설정
        if (trainingTimeService.getAllByMember(member).isEmpty()) {
            TrainingTimeResponseDTO trainingTimeResponseDTO = TrainingTimeResponseDTO.of("", 22, 0);
            BadgeResponseDTO badgeResponseDTO = BadgeResponseDTO.of(memberBadgeService.getBadgeByMemberId(memberId));
            return MemberGetResponseDTO.of(goal, member, trainingTimeResponseDTO,badgeResponseDTO);
        }

        TrainingTime trainingTime = getOneTrainingTime(trainingTimes);
        TrainingTimeResponseDTO trainingTimeResponseDTO = TrainingTimeResponseDTO.of(getDays(trainingTimes), trainingTime.getHour(), trainingTime.getMinute());
        return MemberGetResponseDTO.of(goal, member, trainingTimeResponseDTO, BadgeResponseDTO.of(memberBadgeService.getBadgeByMemberId(memberId)));
    }

    @Transactional
    public void updateMemberPlan(Long memberId, MemberPlanUpdateRequestDTO request) {
        Member member = get(memberId);

        if (nonNull(request.target())) {
            member.updateGoal(request.target());
        }

        if (nonNull(request.hasAlarm())) {
            member.updateHasAlarm(request.hasAlarm());
        }

        if (nonNull(request.trainingTime()) && StringUtils.hasText(request.trainingTime().day())) {
            updateTrainingTime(member, request);
        }
    }

    @Transactional
    public void updateHasAlarm(Long memberId, MemberPushUpdateRequestDTO request) {
        Member member = get(memberId);
        member.updateHasAlarm(request.hasAlarm());
    }

    public MemberNameResponseDTO checkDuplicatedName(String name) {
        boolean isExist = memberRepository.existsByUsername(name);
        return new MemberNameResponseDTO(isExist);
    }

    public Member get(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorMessage.EMPTY_MEMBER.getMessage()));
    }

    private void updateTrainingTime(Member member, MemberPlanUpdateRequestDTO request) {
        trainingTimeService.deleteAll(member);
        for (String day : parseDay(request.trainingTime().day())) {
            TrainingTime trainingTime = TrainingTime.builder()
                    .day(DayType.valueOf(day))
                    .hour(request.trainingTime().hour())
                    .minute(request.trainingTime().minute())
                    .member(member)
                    .build();
            trainingTimeService.save(trainingTime);
        }
    }

    private String[] parseDay(String day) {
        return day.split(",");
    }


    private TrainingTime getOneTrainingTime(List<TrainingTime> trainingTimes) {
        return trainingTimes.stream().findFirst().orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.NOT_SET_TRAINING_TIME.getMessage()));
    }

    private String getDays(List<TrainingTime> trainingTimeList) {
        return trainingTimeList.stream()
                .map(trainingTime -> trainingTime.getDay().name())
                .distinct()
                .collect(Collectors.joining(","));
    }

    private void checkMemberDuplicate(String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new EntityExistsException(ErrorMessage.DUPLICATE_USERNAME.getMessage());
        }
    }
}