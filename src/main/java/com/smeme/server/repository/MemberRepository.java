package com.smeme.server.repository;

import com.smeme.server.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsBySocialId(String socialId);

    Optional<Member> findBySocialId(String socialId);
}
