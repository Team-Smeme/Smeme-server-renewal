package com.smeem.api.member.service;


import com.smeem.domain.member.adapter.trainingtime.TrainingTimeDeleter;
import com.smeem.domain.member.adapter.trainingtime.TrainingTimeFinder;
import com.smeem.domain.member.adapter.trainingtime.TrainingTimeSaver;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.training.model.TrainingTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TrainingTimeService {

    private final TrainingTimeFinder trainingTimeFinder;
    private final TrainingTimeDeleter trainingTimeDeleter;
    private final TrainingTimeSaver trainingTimeSaver;

    public void deleteAllByMember(Member member) {
        trainingTimeDeleter.deleteAllByMember(member);
    }

    protected void deleteAll(Member member) {
        trainingTimeDeleter.deleteAllInBatch(member.getTrainingTimes());
    }

    protected void save(TrainingTime trainingTime) {
        trainingTimeSaver.save(trainingTime);
    }

    protected List<TrainingTime> getAllByMember(Member member) {
        return trainingTimeFinder.findAllByMember(member);
    }

}
