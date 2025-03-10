package com.smeem.application.domain.survey;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CoachingSurvey {
    Long diaryId;
    boolean isSatisfied;
    DissatisfactionType dissatisfactionType;
    String reason;
}
