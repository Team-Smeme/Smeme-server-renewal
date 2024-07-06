package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.member.Member;
import com.smeem.application.port.output.persistence.MemberPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.persistence.postgresql.persistence.entity.MemberEntity;
import com.smeem.persistence.postgresql.persistence.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberAdapter implements MemberPort {
    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> findBySocial(Member.Social social) {
        return memberRepository.findBySocialTypeAndSocialId(social.socialType(), social.socialId())
                .map(MemberEntity::toDomain);
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(MemberEntity.of(member)).toDomain();
    }

    @Override
    public void update(Member member) {
        val foundMember = find(member.getId());
        foundMember.update(member);
    }

    @Override
    public Member findById(long id) {
        return find(id).toDomain();
    }

    @Override
    public void deleteById(long id) {
        //TODO: diary, goal, plan, trainingTime etc...
        memberRepository.deleteById(id);
    }

    @Override
    public boolean isExistByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    @Override
    public int countAll() {
        return (int)memberRepository.count();
    }

    private MemberEntity find(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new SmeemException(ExceptionCode.NOT_FOUND, "id: " + id));
    }
}
