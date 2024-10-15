package com.smeem.output.web.gpt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gpt")
public record OpenAiProperties(
        OpenAI openai
) {

    public record OpenAI(
        String api
    ) {
    }
}
