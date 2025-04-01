package com.smeem.output.web.gpt.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smeem.application.domain.diary.Correction;
import com.smeem.application.port.output.web.openai.OpenAiPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.output.web.gpt.adapter.dto.CorrectionsResponse;
import com.smeem.output.web.gpt.adapter.template.PromptTemplate;
import com.smeem.output.web.gpt.adapter.template.SchemeTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.ai.openai.api.ResponseFormat.Type.JSON_SCHEMA;

@Component
@RequiredArgsConstructor
public class OpenAiAdapter implements OpenAiPort {
    private final ChatClient chatClient;
    private final OpenAiChatModel chatModel;
    private final ObjectMapper objectMapper;

    @Override
    public String prompt(String message) {
        try {
            return chatClient.prompt()
                    .user(message)
                    .call()
                    .content();
        } catch (Exception exception) {
            throw new SmeemException(ExceptionCode.OPEN_AI_SERVICE_AVAILABLE, exception.getMessage());
        }
    }

    @Override
    public List<Correction> promptCorrections(String content) {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .responseFormat(new ResponseFormat(JSON_SCHEMA, SchemeTemplate.getCorrectionScheme()))
                .build();

        Prompt prompt = new Prompt(PromptTemplate.getCorrectionPrompt(content), options);
        ChatResponse call = chatModel.call(prompt);
        String response = call.getResult().getOutput().getText();

        try {
            return objectMapper.readValue(response, CorrectionsResponse.class).results();
        } catch (JsonProcessingException e) {
            throw new SmeemException(ExceptionCode.OPEN_AI_SERVICE_AVAILABLE, e.getMessage());
        }
    }
}
