package com.smeem.domain.member.adapter.member;

import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class MemberDeleter {

    private final MemberRepository memberRepository;

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
