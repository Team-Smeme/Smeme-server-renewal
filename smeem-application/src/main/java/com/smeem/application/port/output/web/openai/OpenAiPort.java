package com.smeem.application.port.output.web.openai;

import com.smeem.application.domain.diary.Correction;

import java.util.List;

public interface OpenAiPort {
    String prompt(String message);
    List<Correction> promptCorrections(String content);
}
