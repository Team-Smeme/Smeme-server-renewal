package com.smeem.api.plan.service;

import com.smeem.api.plan.service.dto.response.PlansAllGetServiceResponse;
import com.smeem.domain.plan.adapter.PlanFinder;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanService {

    private final PlanFinder planFinder;

    public PlansAllGetServiceResponse getAllPlans() {
        val plans = planFinder.findAll();
        return PlansAllGetServiceResponse.of(plans);
    }
}