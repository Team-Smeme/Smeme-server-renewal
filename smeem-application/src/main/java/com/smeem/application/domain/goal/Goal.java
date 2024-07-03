package com.smeem.application.domain.goal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Goal {
    Long id;
    GoalType goalType;
    String way;
    String detail;
}
