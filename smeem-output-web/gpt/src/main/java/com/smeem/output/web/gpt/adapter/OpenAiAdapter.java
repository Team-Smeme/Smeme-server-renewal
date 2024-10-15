package com.smeem.output.web.gpt.adapter;

import com.smeem.application.port.output.web.openai.OpenAiPort;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenAiAdapter implements OpenAiPort {
    private final ChatClient chatClient;

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
}
