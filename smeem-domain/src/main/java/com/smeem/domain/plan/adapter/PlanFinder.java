package com.smeem.domain.plan.adapter;

import com.smeem.domain.plan.exception.PlanException;
import com.smeem.domain.plan.model.Plan;
import com.smeem.domain.plan.repository.PlanRepository;
import com.smeem.domain.support.RepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.smeem.common.code.failure.PlanFailureCode.INVALID_PLAN;
import static org.springframework.data.domain.Sort.Direction.ASC;

@RepositoryAdapter
@RequiredArgsConstructor
public class PlanFinder {

    private final PlanRepository planRepository;

    public Plan findById(final long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new PlanException(INVALID_PLAN));
    }

    public List<Plan> findAll() {
        return planRepository.findAll(Sort.by(ASC, "id"));
    }
}


