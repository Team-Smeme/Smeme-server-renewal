package com.smeem.http.controller;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.port.input.PlanUseCase;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.application.port.input.dto.response.plan.RetrievePlansResponse;
import com.smeem.http.controller.docs.PlanApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/plans")
public class PlanApi implements PlanApiDocs {
    private final PlanUseCase planUseCase;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public SmeemResponse<RetrievePlansResponse> retrievePlans() {
        return SmeemResponse.of(
                planUseCase.retrievePlans(),
                SmeemMessage.RETRIEVE_PLAN);
    }
}
