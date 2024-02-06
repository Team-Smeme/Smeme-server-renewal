package com.smeem.domain.training.repository;


import com.smeem.domain.member.model.Member;
import com.smeem.domain.training.model.TrainingTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingTimeRepository extends JpaRepository<TrainingTime, Long>, TrainingTimeCustomRepository {

    List<TrainingTime> findAllByMember(Member member);

    void deleteAllByMember(Member member);

}
