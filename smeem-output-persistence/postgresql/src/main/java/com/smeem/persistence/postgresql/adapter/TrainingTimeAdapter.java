package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.trainingtime.TrainingTime;
import com.smeem.application.port.output.persistence.TrainingTimePort;
import com.smeem.persistence.postgresql.persistence.entity.TrainingTimeEntity;
import com.smeem.persistence.postgresql.persistence.repository.TrainingTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrainingTimeAdapter implements TrainingTimePort {
    private final TrainingTimeRepository trainingTimeRepository;

    @Override
    public List<TrainingTime> findByMemberId(long memberId) {
        return trainingTimeRepository.findByMemberId(memberId)
                .stream().map(TrainingTimeEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteByMemberId(long memberId) {
        trainingTimeRepository.deleteByMemberId(memberId);
    }

    @Override
    public void saveAll(List<TrainingTime> trainingTimes) {
        trainingTimeRepository.saveAll(trainingTimes.stream().map(TrainingTimeEntity::new).toList());
    }
}
