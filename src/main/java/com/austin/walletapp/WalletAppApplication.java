package com.austin.walletapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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
