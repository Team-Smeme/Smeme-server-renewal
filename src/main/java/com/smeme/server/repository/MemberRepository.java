package com.smeme.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smeme.server.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
