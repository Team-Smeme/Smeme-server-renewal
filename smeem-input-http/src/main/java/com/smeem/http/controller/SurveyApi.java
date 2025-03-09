package com.smeem.http.controller;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.port.input.SurveyUseCase;
import com.smeem.application.port.input.dto.request.survey.CoachingSurveyRequest;
import com.smeem.http.controller.docs.SurveyApiDocs;
import com.smeem.http.controller.dto.SmeemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/survey")
public class SurveyApi implements SurveyApiDocs {
    private final SurveyUseCase surveyUseCase;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/coaching")
    public SmeemResponse<?> surveyCoaching(Principal principal, @RequestBody(required = false) CoachingSurveyRequest request) {
        surveyUseCase.surveyCoaching(request.diaryId(), request);
        return SmeemResponse.of(SmeemMessage.SURVEY_COACHING);
    }
}
