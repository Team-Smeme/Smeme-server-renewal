package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.trainingtime.TrainingTime;

import java.util.List;

public interface TrainingTimePort {
    List<TrainingTime> findByMemberId(long memberId);
    void deleteByMemberId(long memberId);
    void saveAll(List<TrainingTime> trainingTimes);
}
