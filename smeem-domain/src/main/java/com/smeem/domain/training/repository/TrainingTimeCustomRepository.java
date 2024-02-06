package com.smeem.domain.training.repository;

import com.smeem.domain.training.model.TrainingTime;

import java.time.LocalDateTime;
import java.util.List;


public interface TrainingTimeCustomRepository {
    List<TrainingTime> getTrainingTimeForPushAlarm(LocalDateTime now);
}
