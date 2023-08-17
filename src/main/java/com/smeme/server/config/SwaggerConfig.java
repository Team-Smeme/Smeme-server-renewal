package com.smeme.server.config;

import java.util.List;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Authorization",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "Authorization: Bearer ~"
)
public class SwaggerConfig {

	@Value("${server.url}")
	private String serverUrl;

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Smeme Server API V2 Spec")
                .description("Smeme API V2 Document")
                .version("1.0.0");

		Server server = new Server();
		server.setDescription("서버 주소");
		server.setUrl(serverUrl);

        return new OpenAPI()
                .components(new Components())
                .info(info)
				.servers(List.of(server));
    }
}
