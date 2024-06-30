package com.smeem.domain.training.repository;


import com.smeem.domain.persistence.entity.MemberEntity;
import com.smeem.domain.training.model.TrainingTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingTimeRepository extends JpaRepository<TrainingTime, Long> {

    List<TrainingTime> findAllByMember(MemberEntity member);

    void deleteAllByMember(MemberEntity member);

}
