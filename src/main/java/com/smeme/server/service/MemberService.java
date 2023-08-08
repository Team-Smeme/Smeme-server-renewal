package com.smeme.server.service;

import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.dto.member.MemberGetResponseDTO;
import com.smeme.server.dto.member.MemberNameResponseDTO;
import com.smeme.server.dto.member.MemberPlanUpdateRequestDTO;
import com.smeme.server.dto.member.MemberUpdateRequestDTO;
import com.smeme.server.dto.training.TrainingTimeResponseDTO;
import com.smeme.server.model.Member;
import com.smeme.server.model.badge.Badge;
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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequestDTO dto) {
        checkMemberDuplicate(dto.username());
        Member member = getMemberById(memberId);
        member.updateUsername(dto.username());
        member.updateTermAccepted(dto.termAccepted());
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
    public void updateMemberPlan(Long memberId, MemberPlanUpdateRequestDTO requestDTO) {
        Member member = getMemberById(memberId);

        if (nonNull(requestDTO.target())) {
            member.updateGoal(requestDTO.target());
        }

        if (nonNull(requestDTO.hasAlarm())) {
            member.updateHasAlarm(requestDTO.hasAlarm());
        }

        if (nonNull(requestDTO.trainingTime()) && StringUtils.hasText(requestDTO.trainingTime().day())) {
            updateMemberTrainingTime(member, requestDTO);
        }
    }

    public MemberNameResponseDTO checkDuplicatedName(String name) {
        boolean isExist = memberRepository.existsByUsername(name);
        return new MemberNameResponseDTO(isExist);
    }

    private void updateMemberTrainingTime(Member member, MemberPlanUpdateRequestDTO requestDTO) {
        trainingTimeRepository.deleteAll(member.getTrainingTimes());
        for (String day : parseDay(requestDTO.trainingTime().day())) {
            TrainingTime trainingTime = TrainingTime.builder()
                    .day(DayType.valueOf(day))
                    .hour(requestDTO.trainingTime().hour())
                    .minute(requestDTO.trainingTime().minute())
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
}
