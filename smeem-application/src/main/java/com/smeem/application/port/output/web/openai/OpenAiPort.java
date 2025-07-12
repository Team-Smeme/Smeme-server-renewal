package com.smeem.application.port.output.web.openai;

import com.smeem.application.domain.bookmark.Expression;
import com.smeem.application.domain.diary.Correction;

import java.util.List;

public interface OpenAiPort {
    List<Correction> promptCorrections(String content);
    Expression promptExpression(String content);
}
