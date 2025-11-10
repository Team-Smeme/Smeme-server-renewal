package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.member.Member;
import com.smeem.application.domain.withdraw.Withdraw;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberPort {
    Optional<Member> findBySocial(Member.Social social);
    Member save(Member member);
    Member update(Member member);
    Member findById(long id);
    void deleteById(long id);
    boolean isExistByUsername(String username);
    int countAll();
    List<Member> findByTrainingTime(LocalDateTime date);
    void saveWithdraw(Withdraw withdraw);
}
