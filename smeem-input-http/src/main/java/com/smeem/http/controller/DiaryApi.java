package com.smeem.http.controller;

import com.smeem.application.domain.generic.SmeemMessage;
import com.smeem.application.port.input.DiaryUseCase;
import com.smeem.application.port.input.dto.request.diary.WriteDiaryRequest;
import com.smeem.http.controller.dto.SmeemResponse;
import com.smeem.application.port.input.dto.response.diary.RetrieveDiariesResponse;
import com.smeem.application.port.input.dto.response.diary.RetrieveDiaryResponse;
import com.smeem.application.port.input.dto.response.diary.WriteDiaryResponse;
import com.smeem.http.controller.docs.DiaryApiDocs;
import com.smeem.common.util.SmeemConverter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/diaries")
public class DiaryApi implements DiaryApiDocs {
    private final DiaryUseCase diaryUseCase;
    private final SmeemConverter smeemConverter;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SmeemResponse<WriteDiaryResponse> writeDiary(Principal principal, @RequestBody WriteDiaryRequest request) {
        val memberId = smeemConverter.toMemberId(principal);
        return SmeemResponse.of(
                diaryUseCase.writeDiary(memberId, request),
                SmeemMessage.WRITE_DIARY);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{diaryId}")
    public SmeemResponse<RetrieveDiaryResponse> retrieveDiary(@PathVariable long diaryId) {
        return SmeemResponse.of(
                diaryUseCase.retrieveDiary(diaryId),
                SmeemMessage.RETRIEVE_DIARY);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{diaryId}")
    public SmeemResponse<?> modifyDiary(
            Principal principal,
            @PathVariable long diaryId,
            @RequestBody WriteDiaryRequest request
    ) {
        long memberId = smeemConverter.toMemberId(principal);
        diaryUseCase.modifyDiary(memberId, diaryId, request);
        return SmeemResponse.of(SmeemMessage.MODIFY_DIARY);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{diaryId}")
    public SmeemResponse<?> deleteDiary(@PathVariable long diaryId) {
        diaryUseCase.deleteDiary(diaryId);
        return SmeemResponse.of(SmeemMessage.DELETE_MESSAGE);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public SmeemResponse<RetrieveDiariesResponse> retrieveDiariesByTerm(
            Principal principal,
            @RequestParam(name = "start") String startDate,
            @RequestParam(name = "end") String endDate
    ) {
        val memberId = smeemConverter.toMemberId(principal);
        return SmeemResponse.of(
                diaryUseCase.retrieveDiariesByTerm(
                        memberId,
                        LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE),
                        LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE)),
                SmeemMessage.RETRIEVE_DIARY);
    }
}
