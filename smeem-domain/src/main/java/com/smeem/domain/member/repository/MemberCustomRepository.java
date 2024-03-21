package com.smeem.domain.member.repository;

import com.smeem.domain.member.model.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberCustomRepository {
    List<Member> findAllByTrainingTimeForSendingMessage(LocalDateTime now);
}
