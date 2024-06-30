package com.smeem.http.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    private static final String API_TITLE = "SMEEM-API";
    private static final String API_DESCRIPTION = "Smeem Api Document";
    private static final String API_VERSION = "v1";
    private static final String SECURITY_SCHEME = "bearer";
    private static final String BEARER_FORMAT = "JWT";
    private static final String SECURITY_SCHEME_NAME = "BearerAuthentication";

    @Bean
    public OpenAPI openAPI() {
        val info = new Info()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version(API_VERSION);

        val components = new Components().addSecuritySchemes(
                SECURITY_SCHEME_NAME,
                new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(SECURITY_SCHEME)
                        .bearerFormat(BEARER_FORMAT));

        return new OpenAPI()
                .components(components)
                .security(List.of(new SecurityRequirement().addList(SECURITY_SCHEME_NAME)))
                .info(info);
    }
}
