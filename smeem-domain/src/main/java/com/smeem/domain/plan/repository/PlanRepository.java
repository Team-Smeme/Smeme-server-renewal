package com.smeem.domain.plan.repository;

import com.smeem.domain.plan.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
