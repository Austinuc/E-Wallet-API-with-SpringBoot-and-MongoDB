package com.austin.walletapp;

import com.austin.walletapp.enums.Roles;
import com.austin.walletapp.models.User;
import com.austin.walletapp.repositories.UserRepository;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WalletAppApplication {
    private static final String SCHEME_NAME = "Authorization";
    private static final String SCHEME = "basic";

    public static void main(String[] args) {
        SpringApplication.run(WalletAppApplication.class, args);
    }


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


//    @Bean
//    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//            userRepository.save(
//                    User.builder()
//                            .firstName("Admin")
//                            .lastName("Igboke")
//                            .email("austin5astro@gmail.com")
//                            .userName("AustinUc")
//                            .roles(Roles.ADMIN.name())
//                            .password(passwordEncoder.encode("password123"))
//                            .build());
//            userRepository.save(
//                    User.builder()
//                            .firstName("User")
//                            .lastName("Igboke")
//                            .email("augustineigboke@gmail.com")
//                            .userName("Uche")
//                            .roles(Roles.USER.name())
//                            .password(passwordEncoder.encode("password"))
//                            .build());
//        };
//    }
}
