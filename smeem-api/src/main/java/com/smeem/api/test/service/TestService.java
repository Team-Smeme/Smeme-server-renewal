package com.smeem.api.test.service;

import com.smeem.api.test.service.dto.request.TestPushAlarmServiceRequest;
import com.smeem.common.config.ValueConfig;
import com.smeem.domain.member.exception.MemberException;
import com.smeem.domain.member.model.Member;
import com.smeem.domain.member.repository.MemberRepository;
import com.smeem.external.firebase.NotificationService;
import com.smeem.external.firebase.dto.request.NotificationSingleRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.smeem.common.code.failure.MemberFailureCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestService {

    private final MemberRepository memberRepository;

    private final NotificationService notificationService;
    private final ValueConfig valueConfig;

    public void sendMessage(final TestPushAlarmServiceRequest request) {
        val member = findMember(request.memberId());
        val title = valueConfig.getNOTIFICATION_TITLE();
        val body = valueConfig.getNOTIFICATION_BODY();
        notificationService.sendMessage(NotificationSingleRequest.of(member.getFcmToken(), title, body));
    }

    private Member findMember(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(INVALID_MEMBER));
    }
}