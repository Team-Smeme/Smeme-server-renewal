package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.plan.Plan;

public interface PlanPort {
    Plan findById(long id);
}
