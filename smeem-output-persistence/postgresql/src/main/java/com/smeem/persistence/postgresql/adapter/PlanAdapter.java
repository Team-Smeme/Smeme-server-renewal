package com.smeem.persistence.postgresql.adapter;

import com.smeem.application.domain.plan.Plan;
import com.smeem.application.port.output.persistence.PlanPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.persistence.postgresql.persistence.entity.PlanEntity;
import com.smeem.persistence.postgresql.persistence.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlanAdapter implements PlanPort {
    private final PlanRepository planRepository;

    @Override
    public Plan findById(long id) {
        val foundPlan = find(id);
        return foundPlan.toDomain();
    }

    private PlanEntity find(long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new SmeemException(ExceptionCode.NOT_FOUND, "Plan ID: " + id));
    }
}
