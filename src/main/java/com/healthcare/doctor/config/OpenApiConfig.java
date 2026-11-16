
package com.healthcare.doctor.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI doctorServiceOpenAPI() {

        return new OpenAPI()

                // API Information
                .info(new Info()
                        .title("Healthcare Doctor Service API")
                        .description(
                                "REST APIs for Doctor Management, "
                                        + "Specialization, Qualification, "
                                        + "Doctor Availability and Hospital Mapping."
                        )
                        .version("1.0.0")

                        // Contact Information
                        .contact(new Contact()
                                .name("Healthcare Development Team")
                                .email("support@healthcare.com")
                        )

                        // License Information
                        .license(new License()
                                .name("Internal Project")
                        )
                )

                // JWT Security Requirement
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(SECURITY_SCHEME_NAME)
                )

                // JWT Bearer Authentication Scheme
                .components(new Components()
                        .addSecuritySchemes(
                                SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description(
                                                "Enter your JWT access token. "
                                                        + "Do not manually add the Bearer prefix."
                                        )
                        )
                );
    }
}
