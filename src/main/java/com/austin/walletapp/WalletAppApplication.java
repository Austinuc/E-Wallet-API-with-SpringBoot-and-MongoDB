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
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WalletAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletAppApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
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
