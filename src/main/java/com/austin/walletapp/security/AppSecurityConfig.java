package com.austin.walletapp.security;

import com.austin.walletapp.enums.Roles;
import com.austin.walletapp.services.JpaUserDetailsServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AppSecurityConfig {

//    private final PasswordConfig passwordConfig;
    private final JpaUserDetailsServices jpaUserDetailsServices;


    public AppSecurityConfig(JpaUserDetailsServices jpaUserDetailsServices) {
        this.jpaUserDetailsServices = jpaUserDetailsServices;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.regexMatchers(HttpMethod.GET, "/", "index").permitAll();
                    auth.regexMatchers("login*", "signup*", "/css/*", "/js/*", "/v3/api-docs/*",
                            "/swagger-ui/*", "/swagger-ui.html").permitAll();
                    auth.regexMatchers("/api/*").hasRole(Roles.USER.name());
                    auth.anyRequest().authenticated();
                })
                .userDetailsService(jpaUserDetailsServices)
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
