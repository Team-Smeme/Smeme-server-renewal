package com.smeem.output.web.gpt.adapter.dto;

import com.smeem.application.domain.diary.Correction;

import java.util.List;

public record CorrectionsResponse(
        List<Correction> results
) {
}
