package com.smeem.api.plan.api;

import com.smeem.api.common.SuccessResponse;
import com.smeem.api.plan.api.dto.response.PlansAllGetResponse;
import com.smeem.api.plan.service.PlanService;
import com.smeem.api.support.ApiResponseGenerator;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.smeem.common.code.success.PlanSuccessCode.SUCCESS_GET_ALL_PLAN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/plans")
public class PlanController implements PlanApi {

    private final PlanService planService;

    @Override
    @GetMapping
    public ResponseEntity<SuccessResponse<PlansAllGetResponse>> getAllPlans() {
        val response = PlansAllGetResponse.of(planService.getAllPlans());
        return ApiResponseGenerator.success(SUCCESS_GET_ALL_PLAN, response);
    }
}
