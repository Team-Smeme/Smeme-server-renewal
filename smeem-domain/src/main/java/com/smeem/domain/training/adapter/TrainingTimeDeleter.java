package com.smeem.domain.training.adapter;

import com.smeem.domain.member.model.Member;
import com.smeem.domain.support.RepositoryAdapter;
import com.smeem.domain.training.model.TrainingTime;
import com.smeem.domain.training.repository.TrainingTimeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RepositoryAdapter
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
