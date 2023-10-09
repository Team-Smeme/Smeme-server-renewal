package com.smeme.server.service;

import com.smeme.server.model.Diary;
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

    public void deleteAllByMemberId(Long memberId) {
        trainingTimeRepository.deleteAllByMemberId(memberId);
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

    protected List<TrainingTime> getAllByMemberId(Long memberId) {
        return trainingTimeRepository.findAllByMemberId(memberId);
    }

}
