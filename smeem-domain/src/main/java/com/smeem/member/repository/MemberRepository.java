package com.smeem.member.repository;


import com.smeme.server.model.Member;
import com.smeme.server.model.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsBySocialAndSocialId(SocialType socialType, String socialId);

    Optional<Member> findBySocialAndSocialId(SocialType social, String socialId);

    boolean existsByUsername(String username);

}
