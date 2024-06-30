package com.smeem.domain.persistence.repository.member;

import com.smeem.domain.persistence.entity.MemberEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberCustomRepository {
    List<MemberEntity> findAllByTrainingTime(LocalDateTime now);
}
