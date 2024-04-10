package com.smeem.domain.member.adapter.member;

import com.smeem.domain.member.exception.MemberException;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.model.SocialType;
import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

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

    public Member findById(final long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(EMPTY_MEMBER));
    }

    public Member findBySocialAndSocialId(final SocialType socialType, final String socialId) {
        return memberRepository.findBySocialAndSocialId(socialType, socialId)
                .orElseThrow(() -> new MemberException(EMPTY_MEMBER));
    }

    public Member findMemberCanWriteDiaryById(final long id) {
        return memberRepository.findMemberCanWriteDiaryById(id)
                .orElseThrow(() -> new MemberException(CANNOT_WRITE_DIARY));
    }
}


