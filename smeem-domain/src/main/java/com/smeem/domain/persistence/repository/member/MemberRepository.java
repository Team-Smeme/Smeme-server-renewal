package com.smeem.domain.persistence.repository.member;

import com.smeem.api.domain.auth.vo.SocialType;
import com.smeem.domain.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>, MemberCustomRepository {

    boolean existsBySocialAndSocialId(SocialType socialType, String socialId);

    Optional<MemberEntity> findBySocialAndSocialId(SocialType social, String socialId);

    boolean existsByUsername(String username);
}
