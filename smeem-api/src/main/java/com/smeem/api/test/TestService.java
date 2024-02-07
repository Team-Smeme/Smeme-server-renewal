package com.smeem.api.test;


import com.smeem.common.exception.MemberException;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.external.firebase.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.smeem.common.code.failure.MemberFailureCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestService {

    private final MemberRepository memberRepository;
    private final FcmService fcmService;

    public void pushTest(String title, String body, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(INVALID_MEMBER));
        fcmService.pushMessage(member.getFcmToken(), title, body);
    }
}