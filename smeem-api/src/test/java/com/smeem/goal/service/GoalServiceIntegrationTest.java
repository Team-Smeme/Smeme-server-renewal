package com.smeem.goal.service;

import com.smeem.api.goal.service.GoalService;
import com.smeem.api.goal.service.dto.request.GoalGetServiceRequest;
import com.smeem.api.goal.service.dto.response.GoalGetServiceResponse;
import com.smeem.domain.goal.model.Goal;
import com.smeem.domain.goal.model.GoalType;
import com.smeem.domain.goal.repository.GoalRepository;
import com.smeem.support.ServiceIntegrationTest;
import com.smeem.support.fixture.GoalFixture;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GoalServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    GoalService goalService;

    @Autowired
    GoalRepository goalRepository;

    @AfterEach
    void tearDown() {
        goalRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("[성공] 모든 학습 목표를 조회할 수 있다.")
    void getAllGoals() throws Exception {
      // given
        val goal1 = GoalFixture.goal().type(GoalType.DEVELOP).build();
        val goal2 = GoalFixture.goal().type(GoalType.APPLY).build();
        val goal3 = GoalFixture.goal().type(GoalType.BUSINESS).build();
        val goal4 = GoalFixture.goal().type(GoalType.EXAM).build();
        val goal5 = GoalFixture.goal().type(GoalType.NONE).build();
        val goal6 = GoalFixture.goal().type(GoalType.HOBBY).build();
        goalRepository.saveAll(List.of(goal1, goal2, goal3, goal4, goal5, goal6));

      // when
        val response = goalService.getAllGoals();

      // then
        Assertions.assertThat(response.goals())
                .extracting("goalType", "goalDescription")
                .containsExactlyInAnyOrder(
                        Assertions.tuple(GoalType.DEVELOP.name(), GoalType.DEVELOP.getDescription()),
                        Assertions.tuple(GoalType.APPLY.name(), GoalType.APPLY.getDescription()),
                        Assertions.tuple(GoalType.EXAM.name(), GoalType.EXAM.getDescription()),
                        Assertions.tuple(GoalType.BUSINESS.name(), GoalType.BUSINESS.getDescription()),
                        Assertions.tuple(GoalType.NONE.name(), GoalType.NONE.getDescription()),
                        Assertions.tuple(GoalType.HOBBY.name(), GoalType.HOBBY.getDescription())
                );
    }

    @Test
    @DisplayName("[성공] 학습 목표 유형으로 학습 목표를 조회할 수 있다.")
    void getByType() throws Exception {
        // given

        goalRepository.save(GoalFixture.goal().type(GoalType.DEVELOP).build());

        val request = GoalGetServiceRequest.of(GoalType.DEVELOP);

        // when
        final GoalGetServiceResponse response = goalService.getByType(request);

        // then
        Assertions.assertThat(response)
                .extracting("goalType", "way", "detail")
                .containsExactly(GoalType.DEVELOP, "test-goal-way", "test-goal-detail");

    }
}
