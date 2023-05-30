package com.smeme.server.repository.trainingTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.model.training.TrainingTime;

public interface TrainingTimeRepository extends JpaRepository<TrainingTime, Long>, TrainingTimeCustomRepository {
}
