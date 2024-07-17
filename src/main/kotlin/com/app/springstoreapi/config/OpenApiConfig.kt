package com.app.springstoreapi.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    // JWT
    val securitySchemeName = "bearerAuth"

    @Bean
    fun customOpenApi(): OpenAPI{
        return OpenAPI().info(
            Info()
                .title("Store API with Spring Boot and PostgreSQL")
                .version("1.0")
                .description("This is Store API using Spring Boot and PostgreSQL")
        )
            // Add Security JWT
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
            .components(
                Components()
                    .addSecuritySchemes(
                        securitySchemeName,
                        SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .tags(
                listOf(
                    io.swagger.v3.oas.models.tags.Tag().name("Authenticate").description("Authenticate APIs"),
                    io.swagger.v3.oas.models.tags.Tag().name("Category").description("This APIs for managing categories"),
                    io.swagger.v3.oas.models.tags.Tag().name("Product").description("This is APIs for products"),
                )
            )
    }

}