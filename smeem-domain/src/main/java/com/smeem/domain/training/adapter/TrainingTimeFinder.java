package com.smeem.domain.training.adapter;

import com.smeem.domain.member.model.Member;
import com.smeem.domain.support.RepositoryAdapter;
import com.smeem.domain.training.model.TrainingTime;
import com.smeem.domain.training.repository.TrainingTimeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RepositoryAdapter
@RequiredArgsConstructor
public class TrainingTimeFinder {
    private final TrainingTimeRepository trainingTimeRepository;

    public TrainingTime findById(final long id) {
        return trainingTimeRepository.findById(id).orElse(null);
    }

    public List<TrainingTime> findAllByMember(final Member member) {
        return trainingTimeRepository.findAllByMember(member);
    }
}
