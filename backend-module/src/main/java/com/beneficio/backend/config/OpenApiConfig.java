package com.beneficio.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Benefícios API")
                        .version("1.0.0")
                        .description("API REST do sistema de benefícios")
                        .contact(new Contact()
                                .name("Rafael Lucio")
                                .email("rafael@example.com")));
    }

    @Bean
    public GroupedOpenApi beneficiosGroup() {
        return GroupedOpenApi.builder()
                .group("beneficios")
                .pathsToMatch("/beneficios/**")
                .build();
    }
}