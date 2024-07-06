package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.plan.Plan;

import java.util.List;

public interface PlanPort {
    Plan findById(long id);
    List<Plan> findAll();
}
