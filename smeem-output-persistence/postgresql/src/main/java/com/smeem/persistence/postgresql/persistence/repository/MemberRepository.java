package com.smeem.persistence.postgresql.persistence.repository;

import com.smeem.application.domain.member.SocialType;
import com.smeem.persistence.postgresql.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>, MemberCustomRepository {
    Optional<MemberEntity> findBySocialTypeAndSocialId(SocialType social, String socialId);
    boolean existsByUsername(String username);
}
