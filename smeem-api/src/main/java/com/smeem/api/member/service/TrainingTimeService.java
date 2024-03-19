package com.smeem.api.member.service;


import com.smeem.domain.member.model.Member;
import com.smeem.domain.training.model.TrainingTime;
import com.smeem.domain.training.repository.TrainingTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TrainingTimeService {

    private final TrainingTimeRepository trainingTimeRepository;

    public void deleteAllByMember(Member member) {
        trainingTimeRepository.deleteAllByMember(member);
    }

    protected TrainingTime get(Long id) {
        return trainingTimeRepository.findById(id).orElse(null);
    }

    protected void deleteAll(Member member) {
        trainingTimeRepository.deleteAll(member.getTrainingTimes());
    }

    protected void save(TrainingTime trainingTime) {
        trainingTimeRepository.save(trainingTime);
    }

    protected List<TrainingTime> getAllByMember(Member member) {
        return trainingTimeRepository.findAllByMember(member);
    }

}
