package com.smeem.domain.member.adapter.trainingtime;

import com.smeem.domain.training.model.TrainingTime;
import com.smeem.domain.training.repository.TrainingTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingTimeSaver {
    private final TrainingTimeRepository trainingTimeRepository;

    public TrainingTime save(final TrainingTime trainingTime) {
        return trainingTimeRepository.save(trainingTime);
    }
}
