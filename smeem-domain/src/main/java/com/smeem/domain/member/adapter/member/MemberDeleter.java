package com.smeem.domain.member.adapter.member;

import com.smeem.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDeleter {

    private final MemberRepository memberRepository;

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
