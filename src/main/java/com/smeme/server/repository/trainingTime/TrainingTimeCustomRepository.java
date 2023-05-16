package com.smeme.server.repository.trainingTime;

import java.time.LocalDateTime;
import java.util.List;

import com.smeme.server.model.training.TrainingTime;

public interface TrainingTimeCustomRepository {
	List<TrainingTime> getTrainingTimeForPushAlarm(LocalDateTime now);
}
