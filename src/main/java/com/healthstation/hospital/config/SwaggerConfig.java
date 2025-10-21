package com.healthstation.hospital.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "Bearer Token";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // 🔒 Add security requirement
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                // 🔒 Define Bearer Token Scheme
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                // 🩺 General API Info
                .info(new Info()
                        .title("🏥 HealthStation Hospital API")
                        .version("1.0.0")
                        .description("Comprehensive REST API for Hospital Management — includes Admins, Doctors, Patients, Appointments, and Prescriptions.")
                        .contact(new Contact()
                                .name("HealthStation Dev Team")
                                .email("healthstation@demo.com")
                                .url("https://github.com/healthstation"))
                );
    }
}
