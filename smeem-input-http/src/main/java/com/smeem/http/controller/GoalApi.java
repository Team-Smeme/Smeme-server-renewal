package com.smeem.http.controller;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.domain.goal.GoalType;
import com.smeem.application.port.input.GoalUseCase;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.application.port.input.dto.response.goal.RetrieveGoalResponse;
import com.smeem.application.port.input.dto.response.goal.RetrieveGoalsResponse;
import com.smeem.http.controller.docs.GoalApiDocs;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/goals")
public class GoalApi implements GoalApiDocs {
    private GoalUseCase goalUseCase;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public SmeemResponse<RetrieveGoalsResponse> retrieveGoals() {
        return SmeemResponse.of(
                goalUseCase.retrieveGoals(),
                SmeemMessage.RETRIEVE_GOAL);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{type}")
    public SmeemResponse<RetrieveGoalResponse> retrieveGoal(@PathVariable("type") GoalType goalType) {
        return SmeemResponse.of(
                goalUseCase.retrieveGoal(goalType),
                SmeemMessage.RETRIEVE_GOAL);
    }
}
