package com.smeem.http.controller;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.port.input.PlanUseCase;
import com.smeem.application.port.input.dto.response.SmeemResponse;
import com.smeem.application.port.input.dto.response.plan.RetrievePlansResponse;
import com.smeem.http.controller.docs.PlanApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/plans")
public class PlanApi implements PlanApiDocs {
    private final PlanUseCase planUseCase;

    @GetMapping
    public SmeemResponse<RetrievePlansResponse> retrievePlans() {
        return SmeemResponse.of(
                planUseCase.retrievePlans(),
                SmeemMessage.RETRIEVE_PLAN);
    }
}
