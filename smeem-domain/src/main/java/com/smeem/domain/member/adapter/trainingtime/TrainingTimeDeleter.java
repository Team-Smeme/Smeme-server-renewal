package com.smeem.domain.member.adapter.trainingtime;


import com.smeem.domain.member.model.Member;
import com.smeem.domain.training.model.TrainingTime;
import com.smeem.domain.training.repository.TrainingTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainingTimeDeleter {

    private final TrainingTimeRepository trainingTimeRepository;

    public void deleteAllByMember(final Member member) {
        trainingTimeRepository.deleteAllByMember(member);
    }

    public void deleteAllInBatch(List<TrainingTime> trainingTimes) {
        trainingTimeRepository.deleteAllInBatch(trainingTimes);
    }
}
