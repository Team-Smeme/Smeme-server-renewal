package com.smeem.output.web.gpt.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smeem.application.domain.bookmark.model.Expression;
import com.smeem.application.domain.diary.Correction;
import com.smeem.application.port.output.web.openai.OpenAiPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.common.logger.HookLogger;
import com.smeem.common.logger.LoggingMessage;
import com.smeem.output.web.gpt.adapter.dto.CorrectionsResponse;
import com.smeem.output.web.gpt.adapter.template.PromptTemplate;
import com.smeem.output.web.gpt.adapter.template.SchemeTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AbstractMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.ai.openai.api.ResponseFormat.Type.JSON_SCHEMA;

@Component
@RequiredArgsConstructor
public class OpenAiAdapter implements OpenAiPort {

    private final OpenAiChatModel chatModel;
    private final ObjectMapper objectMapper;
    private final HookLogger hookLogger;

    @Override
    public List<Correction> promptCorrections(String content) {
       String response = call(PromptTemplate.getCorrectionPrompt(content), SchemeTemplate.getCorrectionScheme());

       if (response == null) {
           return Collections.emptyList();
       }

        try {
            return objectMapper.readValue(response, CorrectionsResponse.class).results();
        } catch (JsonProcessingException e) {
            throw new SmeemException(ExceptionCode.OPEN_AI_SERVICE_AVAILABLE, e.getMessage());
        }
    }

    @Override
    public Expression promptExpression(String content) {
        String response = call(PromptTemplate.getExpressionPrompt(content), SchemeTemplate.getExpressionScheme());

        if (response == null) {
            return null;
        }

        try {
            Expression expression = objectMapper.readValue(response, Expression.class);
            return expression.validate() ? expression : null;
        } catch (JsonProcessingException e) {
            hookLogger.send(LoggingMessage.error(e, "[OpenAiAdapter][promptExpression]"));
            return null;
        }
    }

    private String call(String prompt, String schema) {
        if (prompt == null || schema == null) {
            return null;
        }

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .responseFormat(new ResponseFormat(JSON_SCHEMA, schema))
                .build();

        return Optional.of(new Prompt(prompt, options))
                .map(chatModel::call)
                .map(ChatResponse::getResult)
                .map(Generation::getOutput)
                .map(AbstractMessage::getText)
                .orElse(null);
    }
}
