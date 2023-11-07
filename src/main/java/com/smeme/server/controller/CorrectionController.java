package com.smeme.server.controller;

import static com.smeme.server.util.ApiResponse.success;
import static com.smeme.server.util.Util.getMemberId;
import static com.smeme.server.util.Util.getURI;
import static com.smeme.server.util.message.ResponseMessage.*;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smeme.server.dto.correction.CorrectionRequestDTO;
import com.smeme.server.dto.correction.CorrectionResponseDTO;
import com.smeme.server.service.CorrectionService;
import com.smeme.server.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/corrections")
public class CorrectionController {

    private final CorrectionService correctionService;

    @Operation(description = "일기 첨삭")
    @PostMapping("/diary/{diaryId}")
    public ResponseEntity<ApiResponse> save(
            Principal principal,
            @PathVariable Long diaryId,
            @RequestBody CorrectionRequestDTO request
    ) {
        CorrectionResponseDTO response = correctionService.save(getMemberId(principal), diaryId, request);
        return ResponseEntity
                .created(getURI(diaryId))
                .body(success(SUCCESS_CREATE_CORRECTION.getMessage(), response));
    }

    @Operation(description = "첨삭 삭제")
    @DeleteMapping("/{correctionId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long correctionId) {
        correctionService.delete(correctionId);
        return ResponseEntity.ok(success(SUCCESS_DELETE_CORRECTION.getMessage()));
    }

    @Operation(description = "첨삭 수정")
    @PatchMapping("/{correctionId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long correctionId, @RequestBody CorrectionRequestDTO request) {
        correctionService.update(correctionId, request);
        return ResponseEntity.ok(success(SUCCESS_UPDATE_CORRECTION.getMessage()));
    }
}
