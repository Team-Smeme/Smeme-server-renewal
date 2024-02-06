package com.smeem.fixture.trainingtime;

import com.smeme.server.dto.training.TrainingTimeRequestDTO;
import com.smeme.server.dto.training.TrainingTimeResponseDTO;
import com.smeem.fixture.member.MemberFixture;
import com.smeme.server.model.training.DayType;
import com.smeme.server.model.training.TrainingTime;

public class TrainingTimeFixture {

    private static final DayType DAY_TYPE = DayType.MON;
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
