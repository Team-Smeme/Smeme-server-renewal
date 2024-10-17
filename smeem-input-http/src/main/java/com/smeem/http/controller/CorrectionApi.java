package com.smeem.http.controller;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.port.input.CorrectionUseCase;
import com.smeem.application.port.input.dto.response.diary.CorrectionsResponse;
import com.smeem.common.util.SmeemConverter;
import com.smeem.http.controller.docs.CorrectionApiDocs;
import com.smeem.http.controller.dto.SmeemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/diaries/{diaryId}")
public class CorrectionApi implements CorrectionApiDocs {
    private final SmeemConverter smeemConverter;
    private final CorrectionUseCase correctionUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SmeemResponse<CorrectionsResponse> correctDiary(Principal principal, @PathVariable long diaryId) {
        long memberId = smeemConverter.toMemberId(principal);
        CorrectionsResponse response = correctionUseCase.correctDiary(memberId, diaryId);
        return SmeemResponse.of(response, SmeemMessage.COACH_DIARY);
    }
}
