package com.smeem.api.diary.dto.request;

import lombok.Builder;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record DiaryListGetServiceRequest(
        long memberId,
        LocalDate startDate,
        LocalDate endDate
) {

    public static DiaryListGetServiceRequest of(long memberId, String startDate, String endDate) {
        return DiaryListGetServiceRequest.builder()
                .memberId(memberId)
                .startDate(LocalDate.parse(startDate, ISO_DATE))
                .endDate(LocalDate.parse(endDate, ISO_DATE))
                .build();
    }
}
