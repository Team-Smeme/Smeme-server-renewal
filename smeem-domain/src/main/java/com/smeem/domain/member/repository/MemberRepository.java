package com.smeem.domain.member.repository;




import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsBySocialAndSocialId(SocialType socialType, String socialId);

    Optional<Member> findBySocialAndSocialId(SocialType social, String socialId);

    boolean existsByUsername(String username);

}
