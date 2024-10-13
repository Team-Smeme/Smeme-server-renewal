package com.smeem.bootstrap.secret;

import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class SecretProcessor implements EnvironmentPostProcessor {
    private static final String PROFILE_SEPARATOR = ",";
    private static final String SMEEM_SECRET_PROPERTY_NAME = "smeemApplicationSecretProperty";
    private static final String PROPERTY_SECRET_MANAGER_TOKEN_NAME = "SECRET_MANAGER_TOKEN";
    private static final String PROPERTY_SECRET_MANAGER_WORKSPACE_ID = "SECRET_MANAGER_WORKSPACE_ID";
    private static final String BEARER_TOKEN_PREFIX = "Bearer";
    private static final String SECRET_MANAGER_URL = "https://app.infisical.com/api/v3";
    private static final Set<String> SUPPORT_PROFILES = Set.of("local", "dev", "prod");

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        val secrets = getSecrets(environment);
        val smeemSecretProperties = new MapPropertySource(
                SMEEM_SECRET_PROPERTY_NAME,
                secrets.stream().collect(Collectors.toMap(Secret::secretKey, Secret::secretValue)));
        environment.getPropertySources().addLast(smeemSecretProperties);
    }

    private List<Secret> getSecrets(ConfigurableEnvironment environment) {
        val activeProfiles = Arrays.stream(environment.getActiveProfiles()).toList();
        log.info("### ACTIVE PROFILES: {} ###", String.join(PROFILE_SEPARATOR, activeProfiles));

        val secretToken = getPropertyOrThrow(environment, PROPERTY_SECRET_MANAGER_TOKEN_NAME);
        val workspaceId = getPropertyOrThrow(environment, PROPERTY_SECRET_MANAGER_WORKSPACE_ID);
        val currentEnvironment = activeProfiles.stream()
                .filter(SUPPORT_PROFILES::contains)
                .findFirst()
                .orElseThrow(() -> new SmeemException(ExceptionCode.INTERNAL_SERVER_ERROR, "No active profile found"));

        val response = secretManagerRestClient(secretToken).get()
                .uri(uriBuilder -> uriBuilder
                        .path("/secrets/raw")
                        .queryParam("workspaceId", workspaceId)
                        .queryParam("environment", currentEnvironment)
                        .build())
                .retrieve()
                .body(SecretResponse.class);

        return response != null ? response.secrets : List.of();
    }

    private String getPropertyOrThrow(ConfigurableEnvironment environment, String propertyName) {
        String property = environment.getProperty(propertyName, String.class);
        if (property == null) {
            throw new SmeemException(ExceptionCode.INTERNAL_SERVER_ERROR, "Secret manager config not set");
        }
        return property;
    }

    private RestClient secretManagerRestClient(String secretToken) {
        return RestClient.builder()
                .defaultHeaders(headers ->
                        headers.add(HttpHeaders.AUTHORIZATION, BEARER_TOKEN_PREFIX + " " + secretToken))
                .baseUrl(SECRET_MANAGER_URL)
                .build();
    }

    private record SecretResponse(
            List<Secret> secrets
    ) {
    }

    private record Secret(
            String id,
            String workspace,
            String environment,
            Long version,
            String secretKey,
            String secretValue
    ) {
    }
}
