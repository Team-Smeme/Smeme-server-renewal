package com.smeme.server.repository.trainingTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.model.training.TrainingTime;

import java.util.List;

public interface TrainingTimeRepository extends JpaRepository<TrainingTime, Long>, TrainingTimeCustomRepository {

    List<TrainingTime> findAllByMemberId(Long memberId);

    void deleteAllByMemberId(Long id);

}
