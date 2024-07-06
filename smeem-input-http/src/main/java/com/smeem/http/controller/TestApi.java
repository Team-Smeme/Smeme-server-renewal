package com.smeem.http.controller;

import java.security.Principal;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.port.output.notification.NotificationPort;
import com.smeem.common.util.SmeemConverter;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.http.controller.docs.TestApiDocs;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/test")
public class TestApi implements TestApiDocs {
    private final NotificationPort notificationPort;
    private final SmeemConverter smeemConverter;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/alarm")
    public SmeemResponse<?> pushAlarm(Principal principal) {
        val memberId = smeemConverter.toMemberId(principal);
        notificationPort.test(memberId);
        return SmeemResponse.of(SmeemMessage.TEST);
    }
}
