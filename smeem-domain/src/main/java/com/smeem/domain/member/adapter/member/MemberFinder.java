package com.smeem.domain.member.adapter.member;

import com.smeem.domain.member.exception.MemberException;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.SocialType;
import com.smeem.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.smeem.common.code.failure.MemberFailureCode.EMPTY_MEMBER;

@Component
@RequiredArgsConstructor
public class MemberFinder {

    private final MemberRepository memberRepository;

    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    public boolean existsBySocialAndSocialId(SocialType socialType, String socialId) {
        return memberRepository.existsBySocialAndSocialId(socialType, socialId);
    }

    public Member findById(final long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(EMPTY_MEMBER));
    }

    public Member findBySocialAndSocialId(final SocialType socialType, final String socialId) {
        return memberRepository.findBySocialAndSocialId(socialType, socialId)
                .orElseThrow(() -> new MemberException(EMPTY_MEMBER));
    }


}


