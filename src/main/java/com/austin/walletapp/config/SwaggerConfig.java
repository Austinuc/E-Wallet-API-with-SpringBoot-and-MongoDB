package com.austin.walletapp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private static final String SCHEME_NAME = "Authorization";
    @Bean
    public OpenAPI springIwalletOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("iWallet API")
                        .description("iWallet API Documentation")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME, createSecurityScheme()))
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME));
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name(SCHEME_NAME)
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER);
    }
}
