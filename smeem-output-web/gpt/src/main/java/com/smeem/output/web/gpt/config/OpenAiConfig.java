package com.smeem.output.web.gpt.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.ApiKey;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static io.micrometer.observation.ObservationRegistry.NOOP;
import static org.springframework.ai.retry.RetryUtils.DEFAULT_RESPONSE_ERROR_HANDLER;
import static org.springframework.ai.retry.RetryUtils.DEFAULT_RETRY_TEMPLATE;
import static org.springframework.util.CollectionUtils.toMultiValueMap;

@Configuration
@EnableConfigurationProperties(OpenAiProperties.class)
public class OpenAiConfig {

    private static final String BASE_URL = "https://api.openai.com";
    private static final String COMPLETIONS_PATH = "/v1/chat/completions";
    private static final String EMBEDDINGS_PATH = "/v1/embeddings";

    @Bean
    OpenAiApi openAiApi(OpenAiProperties properties) {
        ApiKey apiKey = new SimpleApiKey(properties.openai().api());
        return new OpenAiApi(
                BASE_URL,
                apiKey,
                toMultiValueMap(Map.of()),
                COMPLETIONS_PATH,
                EMBEDDINGS_PATH,
                RestClient.builder(),
                WebClient.builder(),
                DEFAULT_RESPONSE_ERROR_HANDLER);
    }

    @Bean
    public OpenAiChatModel openAiChatModel(OpenAiApi openAiApi) {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("gpt-4o-mini")
                .temperature(0.4)
                .build();

        return new OpenAiChatModel(
                openAiApi,
                options,
                DefaultToolCallingManager.builder().build(),
                DEFAULT_RETRY_TEMPLATE,
                NOOP);
    }

    @Bean
    public ChatClient.Builder chatClientBuilder(OpenAiChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem("안녕하세요.");
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }
}
