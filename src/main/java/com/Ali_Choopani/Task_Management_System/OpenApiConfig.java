package com.Ali_Choopani.Task_Management_System;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI configOpenApi() {
        return new OpenAPI() // Displayer all APIs specifications
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // It makes All endpoints to a security schema called (bearerAuth)
                .components(new Components().addSecuritySchemes("bearerAuth", new SecurityScheme() // Creation this  security schema in Swagger and OpenAPI
                        .type(SecurityScheme.Type.HTTP) // To identify kind of this schema (HTTP is used to JWT-Bearer)
                        .scheme("bearer") // To identify the format of Authorization Header -> (Bearer)
                        .bearerFormat("JWT") // For metadata that it tells the format of token is JWT
                        .name("Authorization"))) // The name of header that token is placed in it
                .info(new Info().title("Task Management System").version("1.0")); // Extra descriptions of endpoints (Metadata)
    }
}
