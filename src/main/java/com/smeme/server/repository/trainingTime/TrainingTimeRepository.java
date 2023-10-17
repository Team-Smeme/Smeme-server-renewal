package com.smeme.server.repository.trainingTime;

import com.smeme.server.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.model.training.TrainingTime;

import java.util.List;

public interface TrainingTimeRepository extends JpaRepository<TrainingTime, Long>, TrainingTimeCustomRepository {

    List<TrainingTime> findAllByMember(Member member);

    void deleteAllByMember(Member member);

}
