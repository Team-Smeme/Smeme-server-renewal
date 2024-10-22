package com.smeem.output.web.gpt.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smeem.application.domain.diary.Correction;
import com.smeem.application.port.output.web.openai.OpenAiPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.output.web.gpt.adapter.dto.CorrectionsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionRequest.ResponseFormat;
import org.springframework.stereotype.Component;

import java.util.List;

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
        String jsonSchema = """
                {
                    "type": "object",
                    "properties": {
                        "results": {
                            "type": "array",
                            "items": {
                                "type": "object",
                                "properties": {
                                    "original_sentence": {"type": "string"},
                                    "corrected_sentence": {"type": "string"},
                                    "reason": {"type": "string"}
                                },
                                "required": ["original_sentence", "corrected_sentence", "reason"],
                                "additionalProperties": false
                            }
                        }
                    },
                    "required": ["results"],
                    "additionalProperties": false
                }
        """;

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withResponseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                .build();

        Prompt prompt = new Prompt(getCorrectionPrompt(content), options);
        ChatResponse call = chatModel.call(prompt);
        String response = call.getResult().getOutput().getContent();

        try {
            return objectMapper.readValue(response, CorrectionsResponse.class).results();
        } catch (JsonProcessingException e) {
            throw new SmeemException(ExceptionCode.OPEN_AI_SERVICE_AVAILABLE, e.getMessage());
        }
    }

    private String getCorrectionPrompt(String content) {
        return String.format("""
        Paragraph : %s

        JSON response description:
        - original_sentence: Separate sentence from paragraph, including whitespace, spaces and special characters exactly as they appear in the original.
        - corrected_sentence: corrected sentence including correct expressions.
        - reason: this value MUST be Korean. reason is why the sentence is corrected.
        """, content);
    }
}
