package com.smeme.server.service;

import com.smeme.server.dto.badge.BadgeResponseDTO;
import com.smeme.server.dto.member.MemberGetResponseDTO;
import com.smeme.server.dto.member.MemberUpdateRequestDTO;
import com.smeme.server.dto.training.TrainingTimeResponseDTO;
import com.smeme.server.model.Member;
import com.smeme.server.model.training.TrainingTime;
import com.smeme.server.repository.MemberRepository;
import com.smeme.server.repository.trainingTime.TrainingTimeRepository;
import com.smeme.server.util.message.ErrorMessage;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TrainingTimeRepository trainingTimeRepository;

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequestDTO dto) {
        checkMemberDuplicate(dto.username());
        Member member = getMemberById(memberId);
        member.updateUsername(dto.username());
        member.updateTermAccepted(dto.termAccepted());
    }

    public MemberGetResponseDTO getMember(Long memberId) {
        Member member = getMemberById(memberId);
        List<BadgeResponseDTO> badgeResponseDTOList = member.getBadges().stream().map(BadgeResponseDTO::of).toList();
        List<TrainingTime> trainingTimeList = getTrainingTimeByMemberId(memberId);
        TrainingTime trainingTime = getOneTrainingTime(getTrainingTimeByMemberId(memberId));
        TrainingTimeResponseDTO trainingTimeResponseDTO = TrainingTimeResponseDTO.builder()
                                                            .day(getDays(trainingTimeList))
                                                            .hour(trainingTime.getHour())
                                                            .minute(trainingTime.getMinute())
                                                            .build();
        return MemberGetResponseDTO.of(member, trainingTimeResponseDTO , badgeResponseDTOList);
    }

    private TrainingTime getOneTrainingTime(List<TrainingTime> trainingTimeList) {
        return trainingTimeList.stream().findFirst().orElseThrow(
                () -> new EntityNotFoundException(ErrorMessage.EMPTY_TRAINING_TIME.getMessage()));
    }

    private String getDays(List<TrainingTime> trainingTimeList) {
        return trainingTimeList.stream()
                .map(trainingTime -> trainingTime.getDay().name())
                .distinct()
                .collect(Collectors.joining(", ", "[", "]"));
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
