package com.smeem.domain.member.adapter.member;


import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class MemberSaver {

    private final MemberRepository memberRepository;

    public void save(Member member) {
        memberRepository.save(member);
    }
}
