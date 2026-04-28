package com.tibbelin.tajmtrackr.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
/******************************************************************************
 * Setting up CORS rules for spring security
 * https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html
 *****************************************************************************/

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final SuccessHandler successHandler;
    private final FailureHandler failureHandler;

    public SecurityConfig(SuccessHandler successHandler, FailureHandler failureHandler) {
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())
                .cors(Customizer.withDefaults())
                 .formLogin((form) -> form
                 .successHandler(successHandler)
                 .failureHandler(failureHandler))
                .logout(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
