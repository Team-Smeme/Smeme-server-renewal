package com.smeme.server.repository.trainingTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.model.training.TrainingTime;

import java.util.List;
import java.util.Optional;

public interface TrainingTimeRepository extends JpaRepository<TrainingTime, Long>, TrainingTimeCustomRepository {

    Optional<TrainingTime> findByMemberId(Long memberId);

    List<TrainingTime> findAllByMemberId(Long memberId);

}
