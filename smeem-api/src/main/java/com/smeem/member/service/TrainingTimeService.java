package com.smeem.member.service;

import com.smeme.server.model.Member;
import com.smeme.server.model.training.TrainingTime;
import com.smeme.server.repository.trainingTime.TrainingTimeRepository;
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
