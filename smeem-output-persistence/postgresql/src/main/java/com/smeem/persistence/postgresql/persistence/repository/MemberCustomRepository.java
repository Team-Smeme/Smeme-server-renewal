package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.persistence.postgresql.persistence.entity.MemberEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MemberCustomRepository {
    List<MemberEntity> findByTrainingTime(LocalDateTime trainingTime);
}
