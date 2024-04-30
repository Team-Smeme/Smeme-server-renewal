package com.smeem.domain.plan.repository;

import com.smeem.domain.plan.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("SELECT p FROM Plan p ORDER BY p.id")
    List<Plan> findAllOrderByIdAsc();
}
