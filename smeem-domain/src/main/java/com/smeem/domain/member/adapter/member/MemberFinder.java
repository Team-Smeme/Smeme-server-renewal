package com.smeem.domain.member.adapter.member;

import com.smeem.domain.member.exception.MemberException;
import com.smeem.domain.persistence.entity.MemberEntity;
import com.smeem.api.domain.auth.vo.SocialType;
import com.smeem.domain.persistence.repository.member.MemberRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;
import lombok.val;

import static com.smeem.common.code.failure.MemberFailureCode.CANNOT_WRITE_DIARY;
import static com.smeem.common.code.failure.MemberFailureCode.EMPTY_MEMBER;

@RepositoryAdapter
@RequiredArgsConstructor
public class MemberFinder {

    private final MemberRepository memberRepository;

    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    public boolean existsBySocialAndSocialId(SocialType socialType, String socialId) {
        return memberRepository.existsBySocialAndSocialId(socialType, socialId);
    }

    public MemberEntity findById(final long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(EMPTY_MEMBER));
    }

    public MemberEntity findBySocialAndSocialId(final SocialType socialType, final String socialId) {
        return memberRepository.findBySocialAndSocialId(socialType, socialId)
                .orElseThrow(() -> new MemberException(EMPTY_MEMBER));
    }


}


