package com.smeem.domain.training.adapter;

import com.smeem.domain.support.RepositoryAdapter;
import com.smeem.domain.training.model.TrainingTime;
import com.smeem.domain.training.repository.TrainingTimeRepository;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class TrainingTimeSaver {
    private final TrainingTimeRepository trainingTimeRepository;

    public TrainingTime save(final TrainingTime trainingTime) {
        return trainingTimeRepository.save(trainingTime);
    }
}
