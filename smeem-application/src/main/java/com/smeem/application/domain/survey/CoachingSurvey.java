package com.smeem.application.domain.survey;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CoachingSurvey {
    Long diaryId;
    Boolean isSatisfied;
    List<DissatisfactionType> dissatisfactionTypes;
    String reason;
}
