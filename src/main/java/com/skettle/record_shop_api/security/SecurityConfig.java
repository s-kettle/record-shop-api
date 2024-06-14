package com.skettle.record_shop_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(HttpMethod.DELETE, "/albums/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/albums/**").authenticated()
                .and()
                .oauth2Login();
        return http.build();
    }
}
