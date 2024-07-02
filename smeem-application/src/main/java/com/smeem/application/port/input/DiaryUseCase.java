package com.smeem.application.port.input;

import com.smeem.application.port.input.dto.request.diary.WriteDiaryRequest;
import com.smeem.application.port.input.dto.response.diary.RetrieveDiariesResponse;
import com.smeem.application.port.input.dto.response.diary.RetrieveDiaryResponse;
import com.smeem.application.port.input.dto.response.diary.WriteDiaryResponse;

import java.time.LocalDate;

public interface DiaryUseCase {
    WriteDiaryResponse writeDiary(long memberId, WriteDiaryRequest request);
    RetrieveDiaryResponse retrieveDiary(long diaryId);
    void modifyDiary(long diaryId, WriteDiaryRequest request);
    void deleteDiary(long diary);
    RetrieveDiariesResponse retrieveDiariesByTerm(long memberId, LocalDate startDate, LocalDate endDate);
}
