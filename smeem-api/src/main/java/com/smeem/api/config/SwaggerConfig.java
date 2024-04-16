package com.smeem.api.config;

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
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        val info = new Info()
                .title("Smeem Server API Spec")
                .description("Smeme API Document")
                .version("1.0.0");

        val securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
        val components = new Components()
                .addSecuritySchemes("BearerAuthentication", securityScheme);

        val securityRequirement = new SecurityRequirement()
                .addList("BearerAuthentication");

        return new OpenAPI()
                .components(components)
                .security(List.of(securityRequirement))
                .info(info);
    }
}
