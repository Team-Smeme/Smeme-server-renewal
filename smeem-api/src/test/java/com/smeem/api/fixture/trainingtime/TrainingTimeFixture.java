package com.smeem.api.fixture.trainingtime;

import com.smeem.api.fixture.member.MemberFixture;
import com.smeem.api.member.controller.dto.request.TrainingTimeRequestDTO;
import com.smeem.api.member.controller.dto.response.TrainingTimeResponseDTO;
import com.smeem.domain.training.model.DayType;
import com.smeem.domain.training.model.TrainingTime;

import static com.smeem.domain.training.model.DayType.MON;

public class TrainingTimeFixture {

    private static final DayType DAY_TYPE = MON;
    private static final int HOUR = 10;
    private static final int MINUTE = 30;

    public static TrainingTimeRequestDTO createTrainingTimeRequestDTO() {
        return new TrainingTimeRequestDTO(DAY_TYPE.name(), HOUR, MINUTE);
    }

    public static TrainingTime createTrainingTime() {
        return TrainingTime.builder()
                .day(DAY_TYPE)
                .hour(HOUR)
                .minute(MINUTE)
                .member(MemberFixture.createMember())
                .build();
    }

    public static TrainingTimeResponseDTO createTrainingTimeResponseDTO() {
        return TrainingTimeResponseDTO.of(DAY_TYPE.name(), HOUR, MINUTE);
    }

}
