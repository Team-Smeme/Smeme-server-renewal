package com.smeem.domain.member.adapter.member;


import com.smeem.domain.persistence.entity.MemberEntity;
import com.smeem.domain.persistence.repository.member.MemberRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;

@RepositoryAdapter
@RequiredArgsConstructor
public class MemberSaver {

    private final MemberRepository memberRepository;

    public void save(MemberEntity member) {
        memberRepository.save(member);
    }
}
