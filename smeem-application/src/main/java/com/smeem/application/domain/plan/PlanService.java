package com.smeem.application.domain.plan;

import com.smeem.application.port.input.PlanUseCase;
import com.smeem.application.port.input.dto.response.plan.RetrievePlansResponse;
import com.smeem.application.port.output.persistence.PlanPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService implements PlanUseCase {
    private final PlanPort planPort;

    @Override
    public RetrievePlansResponse retrievePlans() {
        return RetrievePlansResponse.of(planPort.findAll());
    }
}
