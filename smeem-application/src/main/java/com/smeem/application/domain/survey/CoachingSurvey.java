package com.smeem.application.domain.survey;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CoachingSurvey {
    long diaryId;
    boolean isSatisfied;
    List<DissatisfactionType> dissatisfactionTypes;
    String reason;
}
